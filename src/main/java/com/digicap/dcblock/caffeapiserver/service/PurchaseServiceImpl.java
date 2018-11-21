package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.MenuVo;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchasedDto;
import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdVo;
import com.digicap.dcblock.caffeapiserver.dto.UserVo;
import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdDto;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.store.MenuMapper;
import com.digicap.dcblock.caffeapiserver.store.PurchaseMapper;
import com.digicap.dcblock.caffeapiserver.store.ReceiptIdsMapper;
import com.digicap.dcblock.caffeapiserver.store.UserMapper;
import com.digicap.dcblock.caffeapiserver.util.TimeFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {

    private static final String COMPANY_DIGICAP = "digicap";
    private static final String COMPANY_COVISION = "covision";

    private static final int RECEIPT_STATUS_PURCHASE = 0;
    private static final int RECEIPT_STATUS_CANCEL = 1;
    private static final int RECEIPT_STATUS_CANCELED = 1;

    private UserMapper userMapper;

    private PurchaseMapper purchaseMapper;

    private ReceiptIdsMapper receiptMapper;

    private MenuMapper menuMapper;

    private MenuService menuService;

    @Autowired
    public PurchaseServiceImpl(UserMapper userMapper, PurchaseMapper purchaseMapper,
        ReceiptIdsMapper receiptIdsMapper, MenuMapper menuMapper, MenuService menuService) {
        this.userMapper = userMapper;

        this.purchaseMapper = purchaseMapper;

        this.receiptMapper = receiptIdsMapper;

        this.menuMapper = menuMapper;

        this.menuService = menuService;
    }

    public ReceiptIdDto getReceiptId(String rfid) {
        UserVo userVo = null;

        try {
            userVo = userMapper.selectUserByRfid(rfid);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        if (userVo == null) {
            throw new NotFindException("not find user using rfid");
        }

        // ReceiptId 생성.
        int receiptId = 0;

        try {
            receiptId = purchaseMapper.selectReceiptId();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        // receipts table에 insert
        int result = 0;
        try {
           result = receiptMapper.insertReceiptId(userVo.getName(), userVo.getCompany(), receiptId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        if (result == 0) {
            throw new UnknownException("db error");
        }

        ReceiptIdDto receiptIdDto = new ReceiptIdDto();
        receiptIdDto.setReceipt_id(insertZeroString(receiptId));
        receiptIdDto.setName(userVo.getName());
        receiptIdDto.setDate(new TimeFormat().getCurrent());

        return receiptIdDto;
    }

    /**
     * 구매 요청을 처리.
     *
     * @param receiptId 구매하려는 사용자가 발급받은 영수증 ID.
     * @param _purchases 구매목록.
     * @return
     */
    public PurchasedDto requestPurchases(int receiptId, List<LinkedHashMap<String, Object>> _purchases) {
        // parameter 확인
        ReceiptIdVo receiptIdVo = null;

        try {
            receiptIdVo = receiptMapper.selectByReceiptId(receiptId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw e;
        }

        if (receiptIdVo == null) {
            throw new NotFindException("not find receipt_id");
        }

        // receiptId는 구매 API 성공과 상관없이 일회용.
        try {
            receiptMapper.deleteByReceiptId(receiptId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            // 로그만 남기고 프로세스 동작.
        }

        // hashmap to instance
        LinkedList<PurchaseDto> purchases = toPurchaseDtos(_purchases);

        LinkedHashMap<Integer, LinkedList<MenuVo>> menusInCategory = menuService.getAllMenusUsingCode();

        // 구매요청한 카테고리, 메뉴 확인
        for (PurchaseDto purchaseDto : purchases) {
            int category = purchaseDto.getCategory();
            int code = purchaseDto.getCode();
            Integer bExist = menuMapper.existCode(code, category);
            if (bExist == null) {
               throw new InvalidParameterException("unknown category: " + category);
            }

            for (MenuVo menu : menusInCategory.get(category)) {
                // 사용자 정보.
                purchaseDto.setName(receiptIdVo.getName());
                purchaseDto.setUser_record_index(receiptIdVo.getUser_record_index());

                // 구매 정보.
                purchaseDto.setPrice(menu.getPrice());
                purchaseDto.setMenu_name_kr(menu.getName_kr());
                purchaseDto.setReceipt_id(receiptId);

                // DC 가격
                String company = receiptIdVo.getCompany();
                if (company.equals(COMPANY_DIGICAP)) {
                    purchaseDto.setDc_price(menu.getDc_digicap());
                } else if (company.equals(COMPANY_COVISION)) {
                    purchaseDto.setDc_price(menu.getDc_covision());
                } else {
                    purchaseDto.setDc_price(0);
                }
            }
        }

        // Insert Purchases Table.
        for (PurchaseDto p : purchases) {
            try {
                int result = purchaseMapper.insertPurchase(p);
                if (result == 0) {
                    // TODO receipt id db delete
                    throw new UnknownException("DB Insert Error.");
                }
            } catch (Exception e) {
                // TODO receipt id db delete
                throw new UnknownException(e.getMessage());
            }
        }

        PurchasedDto purchasedDto = new PurchasedDto();
        purchasedDto.setTotal_price(calcTotalPrice(purchases));
        purchasedDto.setTotal_dc_price(calcTotalDcPrice(purchases));
        purchasedDto.setPurchased_date(new TimeFormat().getCurrent());
        return purchasedDto;
    }

    /**
     * 구매된 목록 중에서 취소를 처리(취소가 완료가 아닌 취소대기
     *
     * @param receiptId
     * @return
     */
    public List<PurchaseDto> cancelPurchases(int receiptId) {
        try {
            if (!purchaseMapper.existReceiptId(receiptId)) {
                throw new NotFindException("not find receipt_id");
            }
        } catch (NotFindException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new UnknownException(e.getMessage());
        }

        LinkedList<PurchaseDto> results = null;

        try {
            results = purchaseMapper.updateReceiptCancelStatus(receiptId);
            if (results == null || results.size() == 0) {
                throw new NotFindException("not find purchase list using receipt_id");
            }
        } catch (NotFindException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new UnknownException(e.getMessage());
        }

        return results;
    }

    /**
     * 구매취소 요청을 승인
     *
     * @param receiptId receipt id
     * @return
     */
    public List<PurchaseDto> cancelApprovalPurchases(int receiptId) {
        try {
            if (!purchaseMapper.existReceiptId(receiptId)) {
                throw new NotFindException("not find receipt_id");
            }
        } catch (NotFindException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new UnknownException(e.getMessage());
        }

        LinkedList<PurchaseDto> results = null;

        try {
            results = purchaseMapper.updateReceiptCancelApprovalStatus(receiptId);
            if (results == null || results.size() == 0) {
                throw new NotFindException("not find cancel' purchase using receipt_id");
            }
        } catch (NotFindException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new UnknownException(e.getMessage());
        }

        return results;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // private methods

    /**
     * 숫자를 네자리 문자열로 변경. 공백은 0으로 채움.
     *
     * @param number 숫자.
     * @return
     */
    private String insertZeroString(int number) {
        int length = String.valueOf(number).length();

        String value = String.valueOf(number);

        if (length == 3) {
            return "0" + value;
        } else if (length == 2) {
            return "00" + value;
        } else if (length == 1) {
            return "000" + value;
        } else {
            return value;
        }
    }

    private LinkedList<PurchaseDto> toPurchaseDtos(List<LinkedHashMap<String, Object>> purchases) {
        LinkedList<PurchaseDto> results = new LinkedList<>();

        for (LinkedHashMap<String, Object> p : purchases) {
            PurchaseDto pp = new PurchaseDto();
            pp.setCategory(Integer.valueOf(p.getOrDefault("category", -1).toString()));
            pp.setCode(Integer.valueOf(p.getOrDefault("code", -1).toString()));
            pp.setOpt_size(Integer.valueOf(p.getOrDefault("opt_size", -1).toString()));
            pp.setOpt_type(Integer.valueOf(p.getOrDefault("opt_type", -1).toString()));
            pp.setCount(Integer.valueOf(p.getOrDefault("count", -1).toString()));

            results.add(pp);
        }

        return results;
    }

    /**
     * 구매목록에서 총 구매비용을 계산
     *
     * @param purchases purchase list
     * @return total price
     */
    private int calcTotalPrice(LinkedList<PurchaseDto> purchases) {
        int total = 0;

        for (PurchaseDto p : purchases) {
            total += p.getPrice();
        }

        return total;
    }

    /**
     * 구매목록에서 총 할인비용을 계산
     *
     * @param purchases purchase list
     * @return total price
     */
    private int calcTotalDcPrice(LinkedList<PurchaseDto> purchases) {
        int total = 0;

        for (PurchaseDto p : purchases) {
            total += p.getDc_price();
        }

        return total;
    }
}

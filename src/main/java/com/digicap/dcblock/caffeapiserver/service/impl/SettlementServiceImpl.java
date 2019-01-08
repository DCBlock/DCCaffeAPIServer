package com.digicap.dcblock.caffeapiserver.service.impl;

import com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants;
import com.digicap.dcblock.caffeapiserver.dto.*;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.service.SettlementService;
import com.digicap.dcblock.caffeapiserver.store.PurchaseMapper;
import com.digicap.dcblock.caffeapiserver.util.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * 정산 처리 Service Implement.
 */
@Service
@Primary
public class SettlementServiceImpl implements CaffeApiServerApplicationConstants, SettlementService {

    private PurchaseMapper purchaseMapper;

    // -----------------------------------------------------------------------
    // Constructor

    @Autowired
    public SettlementServiceImpl(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }

    // -----------------------------------------------------------------------
    // Public Methods

    /**
     * 사용자의 구매 보고서를 처리.
     *
     * @param w
     * @return
     */
    @Override
    public SettlementUserReportPageDto getReportByRecordIndex(PurchaseWhere w) {
        SettlementUserReportPageDto result = new SettlementUserReportPageDto();

        // Get purchases by user
        try {
            // TODO company
//            LinkedList<PurchaseNewDto> r = purchaseMapper.selectAllUser(w.getBefore(), w.getAfter(), w.getUserRecordIndex(), "");
            LinkedList<PurchaseVo> r = purchaseMapper.selectSearchBy(w);
            if (r == null) {
                throw new NotFindException("not find purchases by user");
            }

            LinkedList<PurchaseSearchDto> purchases = new LinkedList<>();

            // 정의된 응답으로 변경.
            for (PurchaseVo p : r) {
                PurchaseSearchDto ps = new PurchaseSearchDto(p);
                purchases.add(ps);
            }

            // Set
            result.setPurchases(purchases);

            // Set name
            if (r.size() > 0) {
                String name = r.get(0).getName();
                result.setName(name);
            }
        } catch (NotFindException e) {
            throw e;
        } catch (Exception e) {
            throw new UnknownException(e.getMessage());
        }

        // Get totalPage
        try {
            int totalCount = purchaseMapper.selectCount(w);
            int totalPage = totalCount / w.getPerPage();
            if (totalCount % w.getPerPage() > 0) {
                totalCount = ++totalPage;
            }
            result.setTotalPages(totalCount);
        } catch (Exception e) {
            throw e;
        }

        // Get total price
        // Get total dc_price
        try {
            HashMap<String, Long> balance = purchaseMapper.selectBalanceAccounts(w);
            if (balance != null) {
                result.setTotalPrice(balance.getOrDefault("balance", 0L));
                result.setTotalDcPrice(balance.getOrDefault("dcbalance",0L));
            }
        } catch (Exception e) {
            throw e;
        }

        return result;
    }

    /**
     * before ~ after 동안 모든 구매 목록을 구함.
     *
     * @param before
     * @param after
     * @param company
     * @return
     */
    public LinkedList<SettlementReportDto> getReports(Timestamp before, Timestamp after, String company) {
        LinkedList<SettlementReportDto> results = new LinkedList<>();

        // Get purchases
        // user_record_index = -1은 모든 사용자
        LinkedList<PurchaseNewDto> purchases = purchaseMapper.selectAllUser(before, after, -1, company);
        if (purchases == null || purchases.size() == 0)  {
//            throw new NotFindException("not find purchases");
            return results;
        }

        // 사용자별 계산을 위해 임시 정렬 HashMap
        HashMap<Long, List<PurchaseNewDto>> temp = new HashMap<>();

        Iterator<PurchaseNewDto> itr = purchases.iterator();
        while (itr.hasNext()) {
            PurchaseNewDto purchase = itr.next();
            long userRecordIndex = purchase.getUser_record_index();

            int receiptStatus = purchase.getReceipt_status();

            // 구매취소승인은 정산 대상이 아님.
//            if (receiptStatus == RECEIPT_STATUS_CANCELED) {
//                // TODO 만약에 구매취소요청이 이전달 이었다면, 정산총액에서 빼야함.
//                // 이미 이전달에서 구매취소요청 상태로 정산이 처리되었음.
//                // 때문에 이번달 1일보다 이전에 구매취소 구매정보는 price, dc_price를 음수로 변경해야 함.
//                continue;
//            }

            // 정산대상이 아닌 정보가 있는 경우는 exception. 잘못된 정산이 진행되지 않도록.
            if (!(receiptStatus == RECEIPT_STATUS_PURCHASE || receiptStatus == RECEIPT_STATUS_CANCEL
                    || receiptStatus == RECEIPT_STATUS_CANCELED)) {
                throw new UnknownException(String.format("unknown receipt_status(%s)", receiptStatus));
            }

            // 손님 결재는 정산 대상이 아님.
            if (purchase.getPurchase_type() == PURCHASE_TYPE_GUEST) {
                continue;
            }

            if (!temp.containsKey(userRecordIndex)) {
                List<PurchaseNewDto> l = new LinkedList<>();
                l.add(purchase);
                temp.put(userRecordIndex, l);
            } else {
                List<PurchaseNewDto> l = temp.get(userRecordIndex);
                l.add(purchase);
            }
        }

        // 사용자별로 total_price, total_dc_price, billing 계산
        Iterator<List<PurchaseNewDto>> iterator = temp.values().iterator();
        while (iterator.hasNext()) {
            SettlementReportDto s = new SettlementReportDto();

            List<PurchaseNewDto> l = iterator.next();
            for (PurchaseNewDto p : l) {
                s.setName(p.getName());
                s.setEmail(p.getEmail());
                s.setCompany(p.getCompany());
                s.setUserRecordIndex(p.getUser_record_index());

                int status = p.getReceipt_status();

                long price = s.getTotalPrice();
                if (status != RECEIPT_STATUS_CANCELED) {
                    price += (p.getPrice() * p.getCount());
                } else {
                    Timestamp t = new TimeFormat().getCurrentMonthOfStartDay();
                    if (p.getCancel_date().before(t)) {
                        price -= (p.getPrice() * p.getCount());
                    }
                }
                s.setTotalPrice(price);

                long dc = s.getTotalDcPrice();
                if (status != RECEIPT_STATUS_CANCELED) {
                    dc += (p.getDc_price() * p.getCount());
                } else {
                    Timestamp t = new TimeFormat().getCurrentMonthOfStartDay();
                    if (p.getCancel_date().before(t)) {
                        dc -= (p.getDc_price() * p.getCount());
                    }
                }
                s.setTotalDcPrice(dc);
            }

            // 사용자가 결재해야할 금액.
            s.setBillingAmount(s.getTotalPrice() - s.getTotalDcPrice());

            results.add(s);
        }

        return results;
    }

    @Override
    public SettlementReportGuestsDto getReportForGuests(Timestamp before, Timestamp after, long recordIndex) {
        // Set Where for Query
        PurchaseWhere w = PurchaseWhere.builder()
                .page(0) // unused pagination
                .before(before)
                .after(after)
                .company(COMPANY_DIGICAP) // guest purchase only digicap
                .userRecordIndex(recordIndex)
                .purchaseType(PURCHASE_TYPE_GUEST)
                .build();

        SettlementReportGuestsDto result = new SettlementReportGuestsDto();

        // Get Purchases
        LinkedList<PurchaseVo> purchases = purchaseMapper.selectSearchBy(w);
        if (purchases == null) {
            throw new NotFindException("not find purchases for guests");
        }

        // Set User
        if (recordIndex > 0 && purchases.size() > 0) {
            result.setName(purchases.get(0).getName());
            result.setEmail(purchases.get(0).getEmail());
        }

        // Set TotalCount
        result.setTotalCount(purchases.size());

        // Count & Calculation
        long totalPurchasePrice = 0;
        long totalCancelPrice = 0;
        long totalCanceledPrice = 0;

        int totalPurchaseCount = 0;
        int totalCancelCount = 0;
        int totalCanceledCount = 0;

        for (PurchaseVo p : purchases) {
            long temp = p.getPrice() * p.getCount();

            switch (p.getReceipt_status()) {
                case RECEIPT_STATUS_PURCHASE:
                    totalPurchasePrice += temp;
                    totalPurchaseCount++;
                    break;
                case RECEIPT_STATUS_CANCEL:
                    totalCancelPrice += temp;
                    totalCancelCount++;
                    break;
                case RECEIPT_STATUS_CANCELED:
                    totalCanceledPrice += temp;
                    totalCanceledCount++;
                    break;
            }
        }

        // Set Prices
        result.setTotalPurchasePrice(totalPurchasePrice);
        result.setTotalCancelPrice(totalCancelPrice);
        result.setTotalCanceledPrice(totalCanceledPrice);
        result.setTotalPrice(totalPurchasePrice + totalCancelPrice + totalCanceledPrice);

        // Set Counties
        result.setTotalPurchaseCount(totalPurchaseCount);
        result.setTotalCancelCount(totalCancelCount);
        result.setTotalCanceledCount(totalCanceledCount);

        return result;
    }

    @Override
    public PurchaseSearchPageDto getReportsBySearch(PurchaseWhere w) {
        // Query
        LinkedList<PurchaseVo> r = purchaseMapper.selectSearchBy(w);
        if (r == null) {
            throw new NotFindException(String.format("not find purchases for user(%s)", w.getUserRecordIndex()));
        }

        LinkedList<PurchaseSearchDto> purchases = new LinkedList<>();

        // 정의된 응답으로 변경.
        for (PurchaseVo p : r) {
            PurchaseSearchDto ps = new PurchaseSearchDto(p);
            purchases.add(ps);
        }

        PurchaseSearchPageDto result = new PurchaseSearchPageDto();
        result.setList(purchases);

        try {
            int totalCount = purchaseMapper.selectCount(w);
            result.setTotalPages(totalCount);
        } catch (Exception e) {
            throw e;
        }

        return result;
    }

    /**
     * 매월 1일 이월정산 기능
     * @return 이월정산된 purchase table 에 index
     */
    @Override
    public HashMap<Long, Long> getSettleAccount() {
        // 1. get 지난달 모든 구매목록
        // 2. 사용자별(user_record_index) paring

        // 3. paring된 사용자의 총금액이 마이너스면 해당 금액을
        // 4. purchase table 이번달에 insert
        return null;
    }

    // -----------------------------------------------------------------------
    // Private Methods

    /**
     * 구매목록에서 총 구매비용을 계산
     *
     * @param purchases purchase list
     * @return total price
     */
    private long calcTotalPrice(LinkedList<PurchaseSearchDto> purchases) {
        long total = 0;

        for (PurchaseSearchDto p : purchases) {
            if (p.getPurchase_type() == PURCHASE_TYPE_GUEST) {
                continue;
            }
            // 구매, 구매취소는 제외
            switch (p.getReceipt_status()) {
                case RECEIPT_STATUS_PURCHASE:
                case RECEIPT_STATUS_CANCEL:
                    total += (p.getPrice() * p.getCount());
                    break;
            }
        }

        return total;
    }

    /**
     * 구매목록에서 총 할인비용을 계산
     *
     * @param purchases purchase list
     * @return total price
     */
    private long calcTotalDcPrice(LinkedList<PurchaseSearchDto> purchases) {
        long total = 0;

        for (PurchaseSearchDto p : purchases) {
            if (p.getPurchase_type() == PURCHASE_TYPE_GUEST) {
                continue;
            }

            // 구매, 구매취소는 제외
            switch (p.getReceipt_status()) {
                case RECEIPT_STATUS_PURCHASE:
                case RECEIPT_STATUS_CANCEL:
                    total += (p.getDc_price() * p.getCount());
                    break;
            }
        }

        return total;
    }

    /**
     *
     *
     * @param purchases
     * @return
     */
    private long calcTotalCanceledPrice(LinkedList<PurchaseSearchDto> purchases) {
        long v = 0;

        for (PurchaseSearchDto p : purchases) {
            switch (p.getReceipt_status()) {
                case RECEIPT_STATUS_CANCELED: {
                    Timestamp t = new TimeFormat().getCurrentMonthOfStartDay();
                    long temp = t.getTime() / 1_000;
                    if (p.getCancel_date() < temp) {
                        v += (p.getPrice() * p.getCount());
                    }
                    break;
                }
            }
        }

        return v;
    }

    /**
     *
     *
     * @param purchases
     * @return
     */
    private long calcDcTotalCanceledPrice(LinkedList<PurchaseSearchDto> purchases) {
        long v = 0;

        for (PurchaseSearchDto p : purchases) {
            switch (p.getReceipt_status()) {
                case RECEIPT_STATUS_CANCELED: {
                    Timestamp t = new TimeFormat().getCurrentMonthOfStartDay();
                    long temp = t.getTime() / 1_000;
                    if (p.getCancel_date() < temp) {
                        v += (p.getDc_price() * p.getCount());
                    }
                    break;
                }
            }
        }

        return v;
    }
}

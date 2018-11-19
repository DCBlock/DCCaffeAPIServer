package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.UserVo;
import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdDto;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.store.PurchaseMapper;
import com.digicap.dcblock.caffeapiserver.store.ReceiptIdsMapper;
import com.digicap.dcblock.caffeapiserver.store.UserMapper;
import com.digicap.dcblock.caffeapiserver.util.TimeFormat;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {

    private UserMapper userMapper;

    private PurchaseMapper purchaseMapper;

    private ReceiptIdsMapper receiptMapper;

    @Autowired
    public PurchaseServiceImpl(UserMapper userMapper, PurchaseMapper purchaseMapper, ReceiptIdsMapper receiptIdsMapper) {
        this.userMapper = userMapper;

        this.purchaseMapper = purchaseMapper;

        this.receiptMapper = receiptIdsMapper;
    }

    public ReceiptIdDto getReceiptId(String rfid) {
        UserVo userVo = null;

        try {
            userVo = userMapper.existUserByRfid(rfid);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        if (userVo == null) {
            throw new NotFindException("not find user using rfid");
        }

        // ReceptId 생성.
        int receiptId = 0;

        try {
            receiptId = purchaseMapper.getReceiptId();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        // purchase table에 insert
        int result = 0;
        try {
           result = receiptMapper.setReceiptId(userVo.getName(), userVo.getEmail(), receiptId);
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

    public int requestPurchases(int receiptId, List<HashMap<String, Object>> purchases) {
        return 0;
    }

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
}

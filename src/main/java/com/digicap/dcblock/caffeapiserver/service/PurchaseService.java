package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.PurchaseBalanceDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseVo;
import com.digicap.dcblock.caffeapiserver.dto.PurchasedDto;
import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdDto;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public interface PurchaseService {

    ReceiptIdDto getReceiptId(String rfid);

    PurchasedDto requestPurchases(int receiptId, List<LinkedHashMap<String, Object>> purchases);

    List<PurchaseDto> cancelPurchases(int receiptId);

    List<PurchaseDto> cancelApprovalPurchases(int receiptId);

    LinkedList<PurchaseVo> getPurchases(PurchaseDto purchaseDto, Date from, Date to);

    PurchaseBalanceDto getBalanceByRfid(String rfid, Date fromDate, Date toDate);
}

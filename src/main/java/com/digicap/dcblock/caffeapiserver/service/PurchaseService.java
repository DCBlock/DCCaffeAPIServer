package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.PurchaseDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchasedDto;
import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdDto;
import java.util.LinkedHashMap;
import java.util.List;

public interface PurchaseService {

    public ReceiptIdDto getReceiptId(String rfid);

    public PurchasedDto requestPurchases(int receiptId, List<LinkedHashMap<String, Object>> purchases);

    public List<PurchaseDto> cancelPurchases(int receiptId);

    public List<PurchaseDto> cancelApprovalPurchases(int receiptId);
}

package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdDto;
import java.util.HashMap;
import java.util.List;

public interface PurchaseService {

    public ReceiptIdDto getReceiptId(String rfid);

    public int requestPurchases(int receiptId, List<HashMap<String, Object>> purchases);
}

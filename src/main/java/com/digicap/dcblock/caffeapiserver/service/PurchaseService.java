package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdDto;

public interface PurchaseService {

    public ReceiptIdDto getReceiptId(String rfid);
}

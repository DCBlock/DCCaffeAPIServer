package com.digicap.dcblock.caffeapiserver.controller;

import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdDto;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.service.PurchaseService;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/caffe/purchases")
public class PurchaseController {

    private final static String KEY_RFID = "rfid";

    private PurchaseService service;

    @Autowired
    public PurchaseController(PurchaseService service) {
        this.service = service;
    }

    @PostMapping("/receipt/id")
    ReceiptIdDto createReceiptId(@RequestBody Map<String, Object> body) {
        ReceiptIdDto receiptIdDto = null;

        String rfid = Optional.ofNullable(body.get(KEY_RFID))
            .map(Object::toString)
            .orElseThrow(() -> new InvalidParameterException("not find rfid"));

        return receiptIdDto;
    }
}

package com.digicap.dcblock.caffeapiserver.controller;

import com.digicap.dcblock.caffeapiserver.dto.PurchaseDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchasedDto;
import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdDto;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.service.PurchaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/caffe/purchases")
public class PurchaseController {

    // for Request
    private final static String KEY_RFID = "rfid";
    private final static String KEY_PURCHASES = "purchases";

    // for Response
    private final static String KEY_PURCHASE_CANCELS = "purchase_cancels";
    private final static String KEY_PURCHASE_CANCELEDS = "purchase_canceleds";

    private PurchaseService service;

    @Autowired
    public PurchaseController(PurchaseService service) {
        this.service = service;
    }

    @PostMapping("/purchase/receipt/id")
    ReceiptIdDto createReceiptId(@RequestBody Map<String, Object> body) {
        ReceiptIdDto receiptIdDto = null;

        String rfid = Optional.ofNullable(body.get(KEY_RFID))
            .map(Object::toString)
            .orElseThrow(() -> new InvalidParameterException("not find rfid"));

        receiptIdDto = service.getReceiptId(rfid);
        return receiptIdDto;
    }

    @PostMapping("/purchase/receipt/{receiptId}")
    PurchasedDto createPurchasesByReceiptId(@PathVariable("receiptId") String _receiptId, @RequestBody HashMap<String, Object> body) {
        int receiptId = getIntegerValueOf(_receiptId);

        // TODO unsafe code
        List<LinkedHashMap<String, Object>> purchases = Optional.ofNullable(body.get(KEY_PURCHASES))
            .map(s -> new ObjectMapper().convertValue(s, List.class))
            .orElseThrow(() -> new InvalidParameterException("not find purchases."));

        PurchasedDto purchasedDto = service.requestPurchases(receiptId, purchases);
        return purchasedDto;
    }

    @PatchMapping("/purchase/receipt/{receiptId}/cancel")
    HashMap<String, List<PurchaseDto>> cancelPurchaseByReceiptId(@PathVariable("receiptId") String _receiptId) {
        int receiptId = getIntegerValueOf(_receiptId);

        List<PurchaseDto> cancels = service.cancelPurchases(receiptId);

        LinkedHashMap<String, List<PurchaseDto>> result = new LinkedHashMap<>();
        result.put(KEY_PURCHASE_CANCELS, cancels);

        return result;
    }

    @PatchMapping("/purchase/receipt/{receipt_id}/cancel-approval")
    HashMap<String, List<PurchaseDto>> canceledPurchaseByReceiptId(@PathVariable("receiptId") String _receiptId) {
        int receiptId = getIntegerValueOf(_receiptId);

        List<PurchaseDto> canceleds = service.cancelApprovalPurchases(receiptId);

        LinkedHashMap<String, List<PurchaseDto>> result = new LinkedHashMap<>();
        result.put(KEY_PURCHASE_CANCELEDS, canceleds);

        return result;
    }

    private int getIntegerValueOf(String _value) {
        int value = 0;

        try {
            value = Integer.valueOf(_value);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException(e.getMessage());
        }

        return value;
    }
}

package com.digicap.dcblock.caffeapiserver.controller;

import com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchasedDto;
import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdDto;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.service.PurchaseService;
import com.digicap.dcblock.caffeapiserver.service.TemporaryUriService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PurchaseController implements CaffeApiServerApplicationConstants {

    private PurchaseService service;

    @Autowired
    private TemporaryUriService temporaryUriService;

    @Autowired
    public PurchaseController(PurchaseService service) {
        this.service = service;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // 구매 관련 API

    @PostMapping("/api/caffe/purchases/purchase/receipt/id")
    ReceiptIdDto createReceiptId(@RequestBody Map<String, Object> body) {
        ReceiptIdDto receiptIdDto = null;

        String rfid = Optional.ofNullable(body.get(KEY_RFID))
            .map(Object::toString)
            .orElseThrow(() -> new InvalidParameterException("not find rfid"));

        receiptIdDto = service.getReceiptId(rfid);
        return receiptIdDto;
    }

    @PostMapping("/api/caffe/purchases/purchase/receipt/{receiptId}")
    PurchasedDto createPurchasesByReceiptId(@PathVariable("receiptId") String _receiptId, @RequestBody HashMap<String, Object> body) {
        int receiptId = getIntegerValueOf(_receiptId);

        // TODO unsafe code to safe code
        List<LinkedHashMap<String, Object>> purchases = Optional.ofNullable(body.get(KEY_PURCHASES))
            .map(s -> new ObjectMapper().convertValue(s, List.class))
            .orElseThrow(() -> new InvalidParameterException("not find purchases."));

        PurchasedDto purchasedDto = service.requestPurchases(receiptId, purchases);
        return purchasedDto;
    }

    @PatchMapping("/api/caffe/purchases/purchase/receipt/{receiptId}/cancel")
    HashMap<String, List<PurchaseDto>> cancelPurchaseByReceiptId(@PathVariable("receiptId") String _receiptId) {
        int receiptId = getIntegerValueOf(_receiptId);

        List<PurchaseDto> cancels = service.cancelPurchases(receiptId);

        LinkedHashMap<String, List<PurchaseDto>> result = new LinkedHashMap<>();
        result.put(KEY_PURCHASE_CANCELS, cancels);

        return result;
    }

    @PatchMapping("/api/caffe/purchases/purchase/receipt/{receiptId}/cancel-approval")
    HashMap<String, List<PurchaseDto>> canceledPurchaseByReceiptId(@PathVariable("receiptId") String _receiptId) {
        int receiptId = getIntegerValueOf(_receiptId);

        List<PurchaseDto> canceleds = service.cancelApprovalPurchases(receiptId);

        LinkedHashMap<String, List<PurchaseDto>> result = new LinkedHashMap<>();
        result.put(KEY_PURCHASE_CANCELEDS, canceleds);

        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // 구매 token API

    @PostMapping("/api/caffe/purchases/temporary")
    HashMap<String, String> getTemporaryUri(@RequestBody Map<String, Object> body) {
        String rfid = Optional.ofNullable(body.get(KEY_RFID))
            .map(Object::toString)
            .orElseThrow(() -> new InvalidParameterException("not find rfid"));

        String randomUri = temporaryUriService.createTemporaryUri(rfid);
//        temporaryUriService.existTemporary("7179b058-e9df-47a3-b3ca-420260181dfa");
        
        HashMap<String, String> result = new HashMap<>();
        result.put("uri", "http://localhost:8080/" + randomUri);
        return result;
    }

    @GetMapping("/api/caffe/purchases/temporary/{randomUri}")
    void getPurchasesByRandomUri(@PathVariable("randomUri") String randomUri,
        @RequestParam("purchaseBefore") String before,
        @RequestParam("purchaseAfter") String after) {
        //TemporaryUriVo temporaryUriVo = temporaryUriService.existTemporary(randomUri);

        System.out.println("");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // etc

    /**
     *
     *
     * @param _value
     * @return
     */
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

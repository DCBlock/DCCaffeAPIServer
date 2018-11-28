package com.digicap.dcblock.caffeapiserver.controller;

import com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseVo;
import com.digicap.dcblock.caffeapiserver.dto.PurchasedDto;
import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdDto;
import com.digicap.dcblock.caffeapiserver.dto.TemporaryUriVo;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.service.PurchaseService;
import com.digicap.dcblock.caffeapiserver.service.TemporaryUriService;
import com.digicap.dcblock.caffeapiserver.util.ApplicationProperties;
import com.digicap.dcblock.caffeapiserver.util.TimeFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
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

    ApplicationProperties applicationProperties;

    @Autowired
    private TemporaryUriService temporaryUriService;

    @Autowired
    public PurchaseController(ApplicationProperties applicationProperties, PurchaseService service) {
        this.applicationProperties = applicationProperties;
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

        HashMap<String, String> result = new HashMap<>();
        result.put("uri", applicationProperties.getPurchase_list_viewer_server() + randomUri);
        return result;
    }

    @GetMapping("/api/caffe/purchases/temporary/{randomUri}")
    LinkedList<PurchaseVo> getPurchasesByRandomUri(@PathVariable("randomUri") String randomUri,
        @RequestParam("purchaseBefore") String _before,
        @RequestParam("purchaseAfter") String _after) {
        // Check Valid Format
        long before = getLongValueOf(_before);
        long after = getLongValueOf(_after);

        TimeFormat timeFormat = new TimeFormat();
        String from = timeFormat.fromLong(after);
        String to = timeFormat.fromLong(before);

        // Get registered user_record_index and name by random uri.
        TemporaryUriVo temporaryUriVo = temporaryUriService.existTemporary(randomUri);

        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setUser_record_index(0);
        // TODO !!!!! 1:Purchased Status.
        purchaseDto.setReceiptStatus(0);

        LinkedList<PurchaseVo> results = service.getPurchases(purchaseDto, from, to);
        return results;
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

    private long getLongValueOf(String _value) {
        long value = 0;

        try {
            value = Long.valueOf(_value);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException(e.getMessage());
        }

        return value;
    }
}

package com.digicap.dcblock.caffeapiserver.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.digicap.dcblock.caffeapiserver.dto.*;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.service.PurchaseService;
import com.digicap.dcblock.caffeapiserver.service.TemporaryUriService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.Valid;

/**
 * 구매 관련 Controller
 *
 * @author DigiCAP
 */
@RestController
public class PurchaseController implements CaffeApiServerApplicationConstants {

    private PurchaseService service;

    private TemporaryUriService temporaryUriService;

    @Value("${purchase-list-viewer-server}")
    private String viewerServer;

    @Autowired
    public PurchaseController(PurchaseService service, TemporaryUriService temporaryUriService) {
        this.service = service;
        this.temporaryUriService = temporaryUriService;
    }

    // -------------------------------------------------------------------------
    // 구매 관련 API

    @PostMapping(value = "/api/caffe/purchases/purchase/receipt/id", consumes = "application/json; charset=utf-8")
    ReceiptIdDto createReceiptId(@Valid @RequestBody RfidDto rfidDto) {
        ReceiptIdDto receiptIdDto = service.getReceiptId(rfidDto.getRfid());
        return receiptIdDto;
    }

    @PostMapping(value = "/api/caffe/purchases/purchase/receipt/{receiptId}", consumes = "application/json; charset=utf-8")
    PurchasedDto createPurchasesByReceiptId(@PathVariable("receiptId") String _receiptId,
                                            @RequestBody HashMap<String, Object> body) {
        // Check is integer.
        int receiptId = getIntegerValueOf(_receiptId);

        // Casting
        List<LinkedHashMap<String, Object>> purchases = null;

        try {
            List temp = Optional.ofNullable(body.get(KEY_PURCHASES))
                    .map(s -> new ObjectMapper().convertValue(s, List.class))
                    .orElseThrow(() -> new InvalidParameterException("not find purchases."));

            purchases = new ObjectMapper().convertValue(temp,
                    new TypeReference<List<LinkedHashMap<String, Object>>>() {
                    });
        } catch (Exception e) {
            throw new InvalidParameterException("fail casting purchases");
        }

        PurchasedDto purchasedDto = service.requestPurchases(receiptId, purchases);
        return purchasedDto;
    }

    @PatchMapping("/api/caffe/purchases/purchase/receipt/{receiptId}/cancel")
    HashMap<String, List<Purchase2Dto>> cancelPurchaseByReceiptId(@PathVariable("receiptId") String _receiptId) {
        int receiptId = getIntegerValueOf(_receiptId);

        List<PurchaseDto> cancels = service.cancelPurchases(receiptId);

        List<Purchase2Dto> cancels2 = new ArrayList<>();
        for (PurchaseDto p : cancels) {
            cancels2.add(toPurchaseDto(p));
        }

        LinkedHashMap<String, List<Purchase2Dto>> result = new LinkedHashMap<>();
        result.put(KEY_PURCHASE_CANCELS, cancels2);

        return result;
    }

    @PatchMapping("/api/caffe/purchases/purchase/receipt/{receiptId}/cancel-approval")
    HashMap<String, List<Purchase2Dto>> canceledPurchaseByReceiptId(@PathVariable("receiptId") String _receiptId,
                                                                    @RequestParam("purchaseDate") String _purchaseDate) {
        int receiptId = getIntegerValueOf(_receiptId);

        // String to Timestamp
        long temp = getLongValueOf(_purchaseDate);
        Timestamp purchaseTime = new Timestamp(temp * 1000);

        List<PurchaseDto> canceleds = service.cancelApprovalPurchases(receiptId, purchaseTime);

        List<Purchase2Dto> cancels2 = new ArrayList<>();
        for (PurchaseDto p : canceleds) {
            cancels2.add(toPurchaseDto(p));
        }

        LinkedHashMap<String, List<Purchase2Dto>> result = new LinkedHashMap<>();
        result.put(KEY_PURCHASE_CANCELEDS, cancels2);
        return result;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // Purchases API

    @GetMapping("/api/caffe/purchases/purchase/search")
//    LinkedHashMap<String, LinkedHashMap<String, LinkedList<PurchaseSearchDto>>>
    LinkedList<PurchaseSearchDto>
    getPurchases(@RequestParam(value = "before", defaultValue = "") String before,
                 @RequestParam(value = "after", defaultValue = "") String after,
                 @RequestParam(value = "filter", defaultValue = "-2") int filter,
                 @RequestParam(value = "user_index", defaultValue = "0") int userRecordIndex) {
        // Validate Parameter
        if (after.isEmpty()) {
            throw new InvalidParameterException("after is empty");
        }

        if (before.isEmpty()) {
            throw new InvalidParameterException("before is empty");
        }

        if (!(filter == 3 || filter == -1)) {
            throw new InvalidParameterException(String.format("unknown filter(%s)", filter));
        }

        if (filter == -1 && userRecordIndex <= 0) {
            throw new InvalidParameterException(String.format("unknown user_index(%s)", userRecordIndex));
        }

        // String time to java.sql.Date.
        long _from = getLongValueOf(after);
        long _to = getLongValueOf(before);

//        Timestamp _after = new TimeFormat().toTimeStampExcludeTime(_from * 1000);
//        Timestamp _before = new TimeFormat().toTimeStampExcludeTime(_to * 1000);
        Timestamp _after = new Timestamp(_from * 1000);
        Timestamp _before = new Timestamp(_to * 1000);

        // Get Purchases.
//        LinkedHashMap<String, LinkedHashMap<String, LinkedList<PurchaseSearchDto>>> results =
        LinkedList<PurchaseSearchDto> results = service.getPurchasesBySearch(_before, _after, filter, userRecordIndex);
        return results;
    }

    //-----------------------------------------------------------------------------------------------
    // 구매 token API

    @PostMapping(value = "/api/caffe/purchases/temporary", consumes = "application/json; charset=utf-8")
    HashMap<String, String> getTemporaryUri(@RequestBody Map<String, Object> body) {
        // Validation.
        String rfid = Optional.ofNullable(body.get(KEY_RFID))
                .map(Object::toString)
                .orElseThrow(() -> new InvalidParameterException("not find rfid"));

        Timestamp after = Optional.ofNullable(body.get(KEY_PURCHASE_AFTER))
                .map(Objects::toString)
                .map(o -> Long.valueOf(o) * 1000)
                .map(o -> new Timestamp(o))
                .orElseThrow(() -> new InvalidParameterException("not find purchase_after"));

        Timestamp before = Optional.ofNullable(body.get(KEY_PURCHASE_BEFORE))
                .map(Objects::toString)
                .map(o -> Long.valueOf(o) * 1000)
                .map(o -> new Timestamp(o))
                .orElseThrow(() -> new InvalidParameterException("not find purchase_before"));

        String randomUri = temporaryUriService.createTemporaryUri(rfid, after, before);

        HashMap<String, String> result = new HashMap<>();
        result.put("uri", String.format("%s/%s", viewerServer, randomUri));
        return result;
    }

    @GetMapping("/api/caffe/purchases/temporary/{randomUri}")
    LinkedHashMap<String, Object> getPurchasesByUri(@PathVariable("randomUri") String uri) {
        // Get registered user_record_index and name by random uri.
        TemporaryUriDto temporaryUriVo = temporaryUriService.existTemporary(uri);

        // Set Where Case.
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setUser_record_index(temporaryUriVo.getUserRecordIndex());
        purchaseDto.setReceipt_status(RECEIPT_STATUS_PURCHASE);

        // Get Purchased List.
        LinkedList<PurchaseVo> purchases = service.getPurchases(purchaseDto, temporaryUriVo.getSearchDateAfter(),
                temporaryUriVo.getSearchDateBefore());

        List<Purchase2Vo> cancels2 = new ArrayList<>();
        for (PurchaseVo p : purchases) {
            cancels2.add(toPurchase2Vo(p));
        }

        int total = 0;
        int dc_total = 0;

        for (PurchaseVo p : purchases) {
            total += p.getPrice() * p.getCount();
            dc_total += p.getDcPrice() * p.getCount();
        }

        // Result
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        result.put("name", temporaryUriVo.getName());
        result.put("total", total);
        result.put("dc_total", dc_total);
        result.put("purchases", cancels2);
        return result;
    }

    @GetMapping("/api/caffe/purchases/purchase/rfid/{rfid}")
    PurchaseBalanceDto getBalanceByRfid(@PathVariable("rfid") String rfid,
                                        @RequestParam("purchaseBefore") String _before,
                                        @RequestParam("purchaseAfter") String _after) {

        long b = getLongValueOf(_before);
        long a = getLongValueOf(_after);

        Timestamp before = new Timestamp(b * 1000);
        Timestamp after = new Timestamp(a * 1000);

        return service.getBalanceByRfid(rfid, before, after);
    }

    // ---------------------------------------------------------------------------------------------
    // Private Method

    private int getIntegerValueOf(String _value) throws InvalidParameterException {
        try {
            int value = Integer.valueOf(_value);
            return value;
        } catch (NumberFormatException e) {
            throw new InvalidParameterException(e.getMessage());
        }
    }

    private long getLongValueOf(String value) throws InvalidParameterException {
        try {
            long v = Long.valueOf(value);
            return v;
        } catch (NumberFormatException e) {
            throw new InvalidParameterException(e.getMessage());
        }
    }

    private Purchase2Dto toPurchaseDto(PurchaseDto purchaseDto) {
        Purchase2Dto p = new Purchase2Dto();

        p.setCategory(purchaseDto.getCategory());
        p.setCode(purchaseDto.getCode());
        p.setCount(purchaseDto.getCount());
        p.setDc_price(purchaseDto.getDc_price());
        p.setMenu_name_kr(purchaseDto.getMenu_name_kr());
        p.setName(purchaseDto.getName());
        p.setPrice(purchaseDto.getPrice());
        p.setReceipt_id(purchaseDto.getReceipt_id());
        p.setReceipt_status(purchaseDto.getReceipt_id());
        p.setUser_record_index(purchaseDto.getUser_record_index());

        switch (purchaseDto.getOpt_size()) {
            case 0:
                p.setSize(OPT_SIZE_REGULAR);
                break;
            case 1:
                p.setSize(OPT_SIZE_SMALL);
                break;
        }

        switch (purchaseDto.getOpt_type()) {
            case 0:
                p.setType(OPT_TYPE_HOT);
                break;
            case 1:
                p.setType(OPT_TYPE_ICED);
                break;
            case 2:
                p.setType(OPT_TYPE_BOTH);
                break;
        }

        return p;
    }

    private Purchase2Vo toPurchase2Vo(PurchaseVo p) {
        String size = "";
        switch (p.getOptSize()) {
            case 0:
                size = OPT_SIZE_REGULAR;
                break;
            case 1:
                size = OPT_SIZE_SMALL;
                break;
        }

        String type = "";
        switch (p.getOptType()) {
            case 0:
                type = OPT_TYPE_HOT;
                break;
            case 1:
                type = OPT_TYPE_ICED;
                break;
            case 2:
                type = OPT_TYPE_BOTH;
                break;
        }

        // TODO Warning
        return new Purchase2Vo(p.getCode(), p.getPrice(), p.getDcPrice(), type, size, p.getCount(),
                p.getMenuNameKr());
    }
}

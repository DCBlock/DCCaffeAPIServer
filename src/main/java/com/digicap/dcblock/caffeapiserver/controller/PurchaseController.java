package com.digicap.dcblock.caffeapiserver.controller;

import com.digicap.dcblock.caffeapiserver.util.TimeFormat;
import com.google.common.base.Preconditions;
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

    // -------------------------------------------------------------------------
    // Constructor

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
    PurchasedDto createPurchasesByReceiptId(@PathVariable("receiptId") int receiptId,
                                            @RequestBody HashMap<String, Object> body) {
        // Check Argument.
        Preconditions.checkArgument(1 <= receiptId && receiptId <= 999999, "invalid receiptId(%s)", receiptId);

        int type = Integer.valueOf(body.getOrDefault(KEY_PURCHASE_TYPE, -1).toString());
        if (type == -1) {
            throw new InvalidParameterException("not find purchase_type");
        } else if (!(type == PURCHASE_TYPE_MONTH || type == PURCHASE_TYPE_GUEST)) {
            throw new InvalidParameterException(String.format("unknown purchase_type(%d)", type));
        }

        // Casting
        List<LinkedHashMap<String, Object>> purchases = null;

        try {
            List temp = Optional.ofNullable(body.get(KEY_PURCHASES))
                    .map(s -> new ObjectMapper().convertValue(s, List.class))
                    .orElseThrow(() -> new InvalidParameterException("not find purchases."));

            purchases = new ObjectMapper().convertValue(temp, new TypeReference<List<LinkedHashMap<String, Object>>>() {});
        } catch (Exception e) {
            throw new InvalidParameterException("fail casting purchases");
        }

        PurchasedDto purchasedDto = service.requestPurchases(receiptId, type, purchases);
        return purchasedDto;
    }

    @PatchMapping(value = "/api/caffe/purchases/purchase/receipt/{receiptId}/cancel", consumes = "application/json; charset=utf-8")
    PurchaseCancelingDto cancelPurchaseByReceiptId(@PathVariable("receiptId") int receiptId, @RequestBody RfidDto rfidDto) {
        // Check Argument.
        Preconditions.checkArgument(1 <= receiptId && receiptId <= 999999, "invalid receiptId(%s)", receiptId);
        Preconditions.checkNotNull(rfidDto == null || rfidDto.getRfid() == null, "rfid is null");
        Preconditions.checkNotNull(rfidDto.getRfid().isEmpty(), "rfid is empty");

        List<PurchaseDto> cancels = service.cancelPurchases(receiptId, rfidDto.getRfid());

        List<Purchase2Dto> cancels2 = new ArrayList<>();
        for (PurchaseDto p : cancels) {
            cancels2.add(toPurchaseDto(p));
        }

        PurchaseCancelingDto result  = new PurchaseCancelingDto();
        result.setReceiptId(receiptId);
        if (cancels2.size() > 0) {
            result.setPurchasedDate(new TimeFormat().timestampToString(cancels.get(0).getPurchase_date()));
        }
        result.setPurchaseCancels(cancels);

        return result;
    }

    @PatchMapping("/api/caffe/purchases/purchase/receipt/{receiptId}/cancel-approval")
    HashMap<String, List<Purchase2Dto>> canceledPurchaseByReceiptId(@PathVariable("receiptId") int receiptId,
                                                                    @RequestParam("purchaseDate") long purchaseDate) {
        // Check Argument.
        Preconditions.checkArgument(1 <= receiptId && receiptId <= 999999, "invalid receiptId(%s)", receiptId);

        // unix time to Timestamp
        Timestamp purchaseTime = new TimeFormat().toTimeStampExcludeTime(purchaseDate * 1_000);

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
    PurchaseSearchPageDto getPurchases(@RequestParam(value = "before", defaultValue = "0") long before,
                                       @RequestParam(value = "after", defaultValue = "0") long after,
                                       @RequestParam(value = "filter", defaultValue = "-2") int filter,
                                       @RequestParam(value = "user_index", defaultValue = "0") int userRecordIndex,
                                       @RequestParam(value = "company", defaultValue = "") String company,
                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "per_page", defaultValue = "10") int perPage) {
        // Check Argument.
        Preconditions.checkArgument(before > 0, "invalid before(%s)", before);
        Preconditions.checkArgument(after > 0, "invalid after(%s)", after);
        Preconditions.checkArgument(filter == 3 || filter == -1, "unknown filter(%s)", filter);
        Preconditions.checkArgument(page > 0, "page is %s. page start is 1", page);
        Preconditions.checkArgument(10 <= perPage && perPage <= 50, "size is %s. size is 10 ~ 50", perPage);
        Preconditions.checkArgument(before <= after, "before(%s) is bigger than after(%s)", before, after);

        // Check Option Argument.
        if (!company.isEmpty()) { // empty 경우는 무시.
            Preconditions.checkArgument(company.toLowerCase().equals(COMPANY_DIGICAP)
                    || company.toLowerCase().equals(COMPANY_COVISION), "unknown company(%s)", company);
        }

        if (filter == -1 && userRecordIndex <= 0) {
            throw new InvalidParameterException(String.format("unknown user_index(%s)", userRecordIndex));
        }

        // 사용자의 구매목록은 검색조건에서 company를 제외
        if (filter == -1) {
            company = "";
        } else if (filter == 3)  {
            userRecordIndex = 0;
        }

        // Query Where
        PurchaseWhere w = PurchaseWhere.builder()
                .filter(filter)
                .company(company)
                .userRecordIndex(userRecordIndex)
                .before(new Timestamp(before * 1_000L))
                .after(new Timestamp(before * 1_000L))
                .perPage(perPage)
                .page(page)
                .build();

        // Get Purchases.
        PurchaseSearchPageDto result = service.getPurchasesBySearch(w);
        return result;
    }

    //-----------------------------------------------------------------------------------------------
    // 구매 token API

    @PostMapping(value = "/api/caffe/purchases/temporary", consumes = "application/json; charset=utf-8")
    HashMap<String, String> getTemporaryUri(@RequestBody Map<String, Object> body) {
        // Check Argument
        String rfid = Optional.ofNullable(body.get(KEY_RFID))
                .map(Object::toString)
                .orElseThrow(() -> new InvalidParameterException("not find rfid"));

        Timestamp after = Optional.ofNullable(body.get(KEY_PURCHASE_AFTER))
                .map(Objects::toString)
                .map(o -> Long.valueOf(o) * 1_000)
                .map(o -> new Timestamp(o))
                .orElseThrow(() -> new InvalidParameterException("not find purchase_after"));

        Timestamp before = Optional.ofNullable(body.get(KEY_PURCHASE_BEFORE))
                .map(Objects::toString)
                .map(o -> Long.valueOf(o) * 1_000)
                .map(o -> new Timestamp(o))
                .orElseThrow(() -> new InvalidParameterException("not find purchase_before"));

        Preconditions.checkArgument(!before.after(after), "before(%s) is bigger than after(%s)", before.toString(), after.toString());

        String randomUri = temporaryUriService.createTemporaryUri(rfid, before, after);

        HashMap<String, String> result = new HashMap<>();
        result.put("uri", String.format("%s/%s", viewerServer, randomUri));
        return result;
    }

    @GetMapping("/api/caffe/purchases/temporary/{randomUri}")
    LinkedHashMap<String, Object> getPurchasesByTemporaryUri(@PathVariable("randomUri") String uri) {
        // Get registered user_record_index and name by random uri.
        TemporaryUriDto temporaryUriVo = temporaryUriService.existTemporary(uri);

        // Set Where Case.
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setUser_record_index(temporaryUriVo.getUserRecordIndex());
        purchaseDto.setReceipt_status(RECEIPT_STATUS_ALL);

        // Get Purchased List.
        LinkedList<PurchaseNewDto> purchases = service.getPurchases(purchaseDto, temporaryUriVo.getSearchDateBefore(),
                temporaryUriVo.getSearchDateAfter());

        List<PurchasesTemporaryDto> cancels2 = new ArrayList<>();
        for (PurchaseNewDto p : purchases) {
            cancels2.add(new PurchasesTemporaryDto(p));
        }

        long total = 0;
        long dc_total = 0;

        for (PurchasesTemporaryDto p : cancels2) {
            // 구매종류가 Guest는 가격을 계산하지 않음.
            // Guest는 경영지원실에서 결재함.
            if (p.getPurchaseType() == PURCHASE_TYPE_GUEST) {
                continue;
            }

            // 구매취소승인된 것은 계산하지 않음.
            if (p.getReceiptStatus() == CaffeApiServerApplicationConstants.RECEIPT_STATUS_CANCELED) {
                continue;
            }

            total += p.getPrice() * p.getCount();
            dc_total += p.getDc_price() * p.getCount();
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
                                        @RequestParam("purchaseBefore") long _before,
                                        @RequestParam("purchaseAfter") long _after) {
        // Check Augment
        Preconditions.checkArgument(_before > 0, "invalid purchaseBefore(%s)", _before);
        Preconditions.checkArgument(_after > 0, "invalid purchaseAfter(%s)", _after);
        Preconditions.checkArgument(_before <= _after, "purchase_before(%s) is bigger than purchase_after(%s)", _before, _after);

        // long to java.sql.Timestamp.
        Timestamp before = new Timestamp(_before * 1000);
        Timestamp after = new Timestamp(_after * 1000);

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

    private Purchase2Vo toPurchase2Vo(PurchaseOldDto p) {
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
                p.getMenuNameKr(), p.getPurchaseType());
    }
}

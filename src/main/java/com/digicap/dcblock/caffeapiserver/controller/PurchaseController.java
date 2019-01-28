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

import javax.validation.Valid;

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
import com.digicap.dcblock.caffeapiserver.dto.PurchaseBalanceDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseCancelingDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchasePartialDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseSearchPageDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseVo;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseWhere;
import com.digicap.dcblock.caffeapiserver.dto.PurchasedDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchasesTemporaryDto;
import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdDto;
import com.digicap.dcblock.caffeapiserver.dto.RfidDto;
import com.digicap.dcblock.caffeapiserver.dto.TemporaryUriDto;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.service.PurchaseService;
import com.digicap.dcblock.caffeapiserver.service.TemporaryUriService;
import com.digicap.dcblock.caffeapiserver.store.PurchaseMapper;
import com.digicap.dcblock.caffeapiserver.type.PurchaseType;
import com.digicap.dcblock.caffeapiserver.util.TimeFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;

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

    // TODO Service로 옮겨야함
    private PurchaseMapper mapper;
    
    // -------------------------------------------------------------------------
    // Constructor

    @Autowired
    public PurchaseController(PurchaseService service, TemporaryUriService temporaryUriService, PurchaseMapper mapper) {
        this.service = service;
        this.temporaryUriService = temporaryUriService;
        this.mapper = mapper;
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

        PurchaseType purchaseType = PurchaseType.findByType(type);
        if (purchaseType == PurchaseType.EMPTY) {
            throw new InvalidParameterException(String.format("not find purchase_type(%s)", type));
        } else if (purchaseType == PurchaseType.CARRIED) {
            throw new InvalidParameterException(String.format("invalid purchase_type(%s)", type));
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

        PurchasedDto purchasedDto = service.requestPurchases(receiptId, purchaseType, purchases);
        return purchasedDto;
    }

    @PatchMapping(value = "/api/caffe/purchases/purchase/receipt/{receiptId}/cancel", consumes = "application/json; charset=utf-8")
    PurchaseCancelingDto cancelPurchaseByReceiptId(@PathVariable("receiptId") int receiptId, @RequestBody RfidDto rfidDto) {
        // Check Argument.
        Preconditions.checkArgument(1 <= receiptId && receiptId <= 999999, "invalid receiptId(%s)", receiptId);
        Preconditions.checkNotNull(rfidDto == null || rfidDto.getRfid() == null, "rfid is null");
        Preconditions.checkNotNull(rfidDto.getRfid().isEmpty(), "rfid is empty");

        List<PurchaseVo> cancels = service.cancelPurchases(receiptId, rfidDto.getRfid());

        List<PurchasePartialDto> cancels2 = new ArrayList<>();
        for (PurchaseVo p : cancels) {
            cancels2.add(new PurchasePartialDto(p));
        }

        PurchaseCancelingDto result  = new PurchaseCancelingDto();
        result.setReceiptId(receiptId);
        if (cancels.size() > 0) {
            result.setPurchasedDate(new TimeFormat().timestampToString(cancels.get(0).getUpdate_date()));
        }
        
        result.setPurchaseCancels(cancels2);

        return result;
    }

    @PatchMapping("/api/caffe/purchases/purchase/receipt/{receiptId}/cancel-approval")
    HashMap<String, List<PurchasePartialDto>> canceledPurchaseByReceiptId(@PathVariable("receiptId") int receiptId,
                                                                    @RequestParam("purchaseDate") long purchaseDate) {
        // Check Argument.
        Preconditions.checkArgument(1 <= receiptId && receiptId <= 999999, "invalid receiptId(%s)", receiptId);

        // unix time to Timestamp
        Timestamp purchaseTime = new TimeFormat().toTimeStampExcludeTime(purchaseDate * 1_000);

        List<PurchaseVo> canceleds = service.cancelApprovalPurchases(receiptId, purchaseTime);

        List<PurchasePartialDto> cancels2 = new ArrayList<>();
        for (PurchaseVo p : canceleds) {
            cancels2.add(new PurchasePartialDto(p));
        }

        LinkedHashMap<String, List<PurchasePartialDto>> result = new LinkedHashMap<>();
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
                .after(new Timestamp(after * 1_000L))
                .perPage(perPage)
                .page(page)
                .build();

        // Get Purchases.
        PurchaseSearchPageDto result = service.getPurchasesBySearch(w);
        return result;
    }

    //-----------------------------------------------------------------------------------------------
    // 일회용 URI를 이용한 구매목록 API

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
        // TODO 테스트용으로 개발해서 Service에서 비즈니스 로직을 처리하는게 아니라, Controller에서 모두 처리.
        // Get registered user_record_index and name by random uri.
        TemporaryUriDto temporaryUriVo = temporaryUriService.existTemporary(uri);

        // Set Where Case.
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setUser_record_index(temporaryUriVo.getUserRecordIndex());
        purchaseDto.setReceipt_status(RECEIPT_STATUS_ALL);

        // Get Purchased List.
        LinkedList<PurchaseVo> purchases = service.getPurchases(purchaseDto, temporaryUriVo.getSearchDateBefore(),
                temporaryUriVo.getSearchDateAfter());

        List<PurchasesTemporaryDto> cancels2 = new ArrayList<>();
        for (PurchaseVo p : purchases) {
            cancels2.add(new PurchasesTemporaryDto(p));
        }

        // Set Where for Query
        PurchaseWhere w = PurchaseWhere.builder()
                .before(temporaryUriVo.getSearchDateBefore())
                .after(temporaryUriVo.getSearchDateAfter())
                .userRecordIndex(temporaryUriVo.getUserRecordIndex())
                .purchaseType(PURCHASE_TYPE_DEFAULT)
                .receiptStatus(RECEIPT_STATUS_DEFAULT)
                .build();
        
        // Execute Query.
        try {
            HashMap<String, Long> balances = mapper.selectBalanceAccounts(w);

            // Result
            LinkedHashMap<String, Object> result = new LinkedHashMap<>();
            result.put("name", temporaryUriVo.getName());
            if (balances != null) {
                result.put("total", balances.getOrDefault("balance", (long) 0));
                result.put("dc_total", balances.getOrDefault("dcbalance", (long) 0));
            } else {
                result.put("total", 0);
                result.put("dc_total", 0);
            }
            result.put("purchases", cancels2);
            return result;
        } catch (Throwable t) {
            throw new UnknownException(t.getMessage());
        }
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

    
}

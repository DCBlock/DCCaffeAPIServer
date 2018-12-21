package com.digicap.dcblock.caffeapiserver.controller;

import com.digicap.dcblock.caffeapiserver.dto.PurchaseSearchDto;
import com.digicap.dcblock.caffeapiserver.dto.SettlementReportDto;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.service.PurchaseService;
import com.digicap.dcblock.caffeapiserver.service.SettlementService;
import com.digicap.dcblock.caffeapiserver.service.TemporaryUriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.LinkedList;

/**
 * 정산처리 Controller.
 */
@RestController
public class SettlementController {

    private SettlementService settlementService;

    @Autowired
    public SettlementController(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    /**
     * 사용자의 기간별 대금을 정산.
     *
     * @param before
     * @param after
     * @param userRecordIndex
     * @return
     */
    @GetMapping("/api/caffe/settlement/report")
    SettlementReportDto getPurchases(@RequestParam(value = "before", defaultValue = "") String before,
                                     @RequestParam(value = "after", defaultValue = "") String after,
                                     @RequestParam(value = "user_index", defaultValue = "0") long userRecordIndex) {
        // Validate Parameter
        if (before.isEmpty()) {
            throw new InvalidParameterException("before is empty");
        }

        if (after.isEmpty()) {
            throw new InvalidParameterException("after is empty");
        }

        if (userRecordIndex <= 0) {
            throw new InvalidParameterException(String.format("invalid user_index(%s)", userRecordIndex));
        }

        // time(String) to java.sql.Timestamp
        Timestamp a = getTimestampValueOf(after);
        Timestamp b = getTimestampValueOf(before);

        SettlementReportDto result = settlementService.getReportByRecordIndex(b, a, userRecordIndex);
        return result;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // Private Methods

    private long getLongValueOf(String value) throws InvalidParameterException {
        try {
            long v = Long.valueOf(value);
            return v;
        } catch (NumberFormatException e) {
            throw new InvalidParameterException(e.getMessage());
        }
    }

    private Timestamp getTimestampValueOf(String value) {
        long t = getLongValueOf(value);
        return new Timestamp(t * 1000);
    }
}

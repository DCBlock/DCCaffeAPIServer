package com.digicap.dcblock.caffeapiserver.service.impl;

import com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseNewDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseSearchDto;
import com.digicap.dcblock.caffeapiserver.dto.SettlementReportDto;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.service.SettlementService;
import com.digicap.dcblock.caffeapiserver.store.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.LinkedList;

/**
 * 정산 처리 Service Implement.
 */
@Service
@Primary
public class SettlementServiceImpl implements CaffeApiServerApplicationConstants, SettlementService {

    private PurchaseMapper purchaseMapper;

    // -----------------------------------------------------------------------
    // Constructor

    @Autowired
    public SettlementServiceImpl(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }

    // -----------------------------------------------------------------------
    // Public Methods

    /**
     * 사용자의 구매 보고서를 처리.
     *
     * @param before
     * @param after
     * @param recordIndex
     * @return
     */
    @Override
    public SettlementReportDto getReportByRecordIndex(Timestamp before, Timestamp after, long recordIndex) {
        SettlementReportDto reportDto = new SettlementReportDto();

        // Get purchases by user
        try {
            LinkedList<PurchaseNewDto> r = purchaseMapper.selectAllUser(before, after, recordIndex);
            if (r == null || r.size() == 0)  {
                throw new NotFindException("not find purchases by user");
            }

            LinkedList<PurchaseSearchDto> results = new LinkedList<>();

            // 정의된 응답으로 변경.
            for (PurchaseNewDto p : r) {
                PurchaseSearchDto ps = new PurchaseSearchDto(p);
                results.add(ps);
            }

            // Set
            reportDto.setPurchases(results);
        } catch (NotFindException e) {
            throw e;
        } catch (Exception e) {
            throw new UnknownException(e.getMessage());
        }

        // Get total price
        long price = calcTotalPrice(reportDto.getPurchases());
        reportDto.setTotalPrice(price);

        // Get total dc_price
        price = calcTotalDcPrice(reportDto.getPurchases());
        reportDto.setTotalDcPrice(price);

        // Set time
        reportDto.setBeforeDate(before.getTime());
        reportDto.setAfterDate(after.getTime());

        // Set name
        return reportDto;
    }

    /**
     * 구매목록에서 총 구매비용을 계산
     *
     * @param purchases purchase list
     * @return total price
     */
    private long calcTotalPrice(LinkedList<PurchaseSearchDto> purchases) {
        long total = 0;

        for (PurchaseSearchDto p : purchases) {
            // 구매, 구매취소는 제외
            switch (p.getReceipt_status()) {
                case RECEIPT_STATUS_PURCHASE:
                case RECEIPT_STATUS_CANCEL:
                    total += (p.getPrice() * p.getCount());
                    break;
            }
        }

        return total;
    }

    /**
     * 구매목록에서 총 할인비용을 계산
     *
     * @param purchases purchase list
     * @return total price
     */
    private long calcTotalDcPrice(LinkedList<PurchaseSearchDto> purchases) {
        long total = 0;

        for (PurchaseSearchDto p : purchases) {
            // 구매, 구매취소는 제외
            switch (p.getReceipt_status()) {
                case RECEIPT_STATUS_PURCHASE:
                case RECEIPT_STATUS_CANCEL:
                    total += (p.getDc_price() * p.getCount());
                    break;
            }
        }

        return total;
    }
}

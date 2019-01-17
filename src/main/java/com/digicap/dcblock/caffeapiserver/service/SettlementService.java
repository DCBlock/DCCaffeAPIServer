package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.*;

import java.sql.Timestamp;
import java.util.LinkedList;

public interface SettlementService {

    LinkedList<SettlementReportDto> getReports(Timestamp before, Timestamp after, String company);

    SettlementUserReportPageDto getReportByRecordIndex(PurchaseWhere w);

    SettlementReportGuestsDto getReportForGuests(Timestamp before, Timestamp after, long recordIndex);

    PurchaseSearchPageDto getReportsBySearch(PurchaseWhere w);

    /**
     * 지난달 사용자별 이용금액에서 이월 대상, 이월금액을 처리.
      * @return Carried forwarded Count of User
     */
    int getBalanceAccountLastMonth();
}

package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;

public interface SettlementService {

    LinkedList<SettlementReportDto> getReports(Timestamp before, Timestamp after, String company);

    SettlementUserReportPageDto getReportByRecordIndex(PurchaseWhere w);

    SettlementReportGuestsDto getReportForGuests(Timestamp before, Timestamp after, long recordIndex);

    PurchaseSearchPageDto getReportsBySearch(PurchaseWhere w);

    HashMap<Long, Long> getSettleAccount();
}

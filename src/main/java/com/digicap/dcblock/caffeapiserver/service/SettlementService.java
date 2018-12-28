package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.SettlementReportDto;
import com.digicap.dcblock.caffeapiserver.dto.SettlementUserReportDto;

import java.sql.Timestamp;
import java.util.LinkedList;

public interface SettlementService {

    LinkedList<SettlementReportDto> getReports(Timestamp before, Timestamp after);

    SettlementUserReportDto getReportByRecordIndex(Timestamp before, Timestamp after, long recordIndex);
}

package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.SettlementReportDto;

import java.sql.Timestamp;

public interface SettlementService {

    SettlementReportDto getReportByRecordIndex(Timestamp before, Timestamp after, long recordIndex);
}

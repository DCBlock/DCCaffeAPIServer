package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.SettlementUserReportDto;

import java.sql.Timestamp;

public interface SettlementService {

    SettlementUserReportDto getReportByRecordIndex(Timestamp before, Timestamp after, long recordIndex);
}

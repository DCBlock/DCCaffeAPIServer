package com.digicap.dcblock.caffeapiserver.service;

import java.sql.Timestamp;

import com.digicap.dcblock.caffeapiserver.dto.TemporaryUriDto;

/**
 * TemporaryUri Service Interface
 */
public interface TemporaryUriService {

    String createTemporaryUri(String rfid, Timestamp before, Timestamp after);

    TemporaryUriDto existTemporary(String uuid);
}

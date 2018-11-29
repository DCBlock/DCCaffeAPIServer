package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.TemporaryUriVo;
import java.sql.Timestamp;

/**
 * TemporaryUri Service Interface
 */
public interface TemporaryUriService {

    String createTemporaryUri(String rfid, Timestamp after, Timestamp before);

    TemporaryUriVo existTemporary(String uuid);
}

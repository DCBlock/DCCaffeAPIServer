package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.TemporaryUriVo;

/**
 * TemporaryUri Service Interface
 */
public interface TemporaryUriService {

    String createTemporaryUri(String rfid);

    TemporaryUriVo existTemporary(String uuid);
}

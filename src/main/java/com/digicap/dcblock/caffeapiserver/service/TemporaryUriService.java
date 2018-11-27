package com.digicap.dcblock.caffeapiserver.service;

/**
 * TemporaryUri Service Interface
 */
public interface TemporaryUriService {

    String createTemporaryUri(String rfid);

    boolean existTemporary(String uuid);
}

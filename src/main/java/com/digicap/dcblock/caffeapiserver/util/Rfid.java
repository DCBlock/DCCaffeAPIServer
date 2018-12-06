package com.digicap.dcblock.caffeapiserver.util;

import java.security.InvalidParameterException;
import lombok.Getter;

/**
 * RFID 이용한 처리에 필요한 기능.
 */
@Getter
public class Rfid {

    private static final int LENGTH = 10;

    private String rfid;

    public Rfid(String rfid) {
        if (rfid.length() != LENGTH) {
            throw new InvalidParameterException("RFID length is 10.");
        }

        try {
            Integer.valueOf(rfid);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("RFID only Number String.");
        }
    }
}

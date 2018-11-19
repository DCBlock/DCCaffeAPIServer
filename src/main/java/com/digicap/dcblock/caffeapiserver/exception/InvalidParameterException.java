package com.digicap.dcblock.caffeapiserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class InvalidParameterException extends java.security.InvalidParameterException {

    @Getter
    private String reason;
}

package com.digicap.dcblock.caffeapiserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class InvalidParameterException extends RuntimeException {

    @Getter
    private String reason;
}

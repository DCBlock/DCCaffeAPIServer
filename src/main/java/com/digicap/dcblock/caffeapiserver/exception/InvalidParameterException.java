package com.digicap.dcblock.caffeapiserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class InvalidParameterException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 7747637121997572488L;

    @Getter
    private String reason;
}

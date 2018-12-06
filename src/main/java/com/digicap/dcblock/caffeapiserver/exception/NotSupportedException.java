package com.digicap.dcblock.caffeapiserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class NotSupportedException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 3648622696230356783L;

    @Getter
    private String reason;
}

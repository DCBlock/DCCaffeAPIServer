package com.digicap.dcblock.caffeapiserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ExpiredTimeException extends RuntimeException {

    @Getter
    private String reason;
}

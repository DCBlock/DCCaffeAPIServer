package com.digicap.dcblock.caffeapiserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ForbiddenException extends RuntimeException {

    @Getter
    private String reason;
}

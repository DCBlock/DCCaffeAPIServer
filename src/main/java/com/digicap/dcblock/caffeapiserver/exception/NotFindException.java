package com.digicap.dcblock.caffeapiserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class NotFindException extends Exception {

    @Getter
    private String reason;
}

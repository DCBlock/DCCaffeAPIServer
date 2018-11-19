package com.digicap.dcblock.caffeapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Error Response Body DTO.
 */
@AllArgsConstructor
public class ApiError {

    @Getter
    private int code;

    @Getter
    private String reason;
}
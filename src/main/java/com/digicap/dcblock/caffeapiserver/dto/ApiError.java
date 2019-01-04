package com.digicap.dcblock.caffeapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Error Response Body DTO.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

  @Getter
  private int code;

  @Getter
  private String reason;
}
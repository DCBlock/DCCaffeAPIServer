package com.digicap.dcblock.caffeapiserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class NotFindException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = 1496622881255660633L;

  @Getter
  private String reason;
}

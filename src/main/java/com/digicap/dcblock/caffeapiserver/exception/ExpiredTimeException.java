package com.digicap.dcblock.caffeapiserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ExpiredTimeException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = -4771844089258190065L;

  @Getter
  private String reason;
}

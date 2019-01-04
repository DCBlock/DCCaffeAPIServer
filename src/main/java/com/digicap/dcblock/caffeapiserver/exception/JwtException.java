package com.digicap.dcblock.caffeapiserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class JwtException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = 6748457581314538191L;

  @Getter
  private String reason;
}

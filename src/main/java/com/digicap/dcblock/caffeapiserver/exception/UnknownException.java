package com.digicap.dcblock.caffeapiserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class UnknownException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = -6700065420484561401L;

  @Getter
  private String reason;
}

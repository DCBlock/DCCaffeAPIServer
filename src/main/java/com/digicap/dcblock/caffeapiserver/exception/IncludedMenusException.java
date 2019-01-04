package com.digicap.dcblock.caffeapiserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class IncludedMenusException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = -8238523850906116370L;

  @Getter
  private String reason;
}

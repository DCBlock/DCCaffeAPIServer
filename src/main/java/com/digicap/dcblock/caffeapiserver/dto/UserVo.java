package com.digicap.dcblock.caffeapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserVo {

  private String name;

  private String company;

  private long index;
}

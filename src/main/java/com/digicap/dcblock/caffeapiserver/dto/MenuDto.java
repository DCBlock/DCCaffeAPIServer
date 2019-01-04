package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MenuDto {

  private int category;

  private int code;

  @JsonProperty(access = Access.WRITE_ONLY)
  private int order = -1;

  private String name_kr;

  private String name_en;

  private int price = -1;

  private int dc_digicap = -1;

  private int dc_covision = -1;

  @JsonProperty("type")
  private String opt_type;

  @JsonProperty("size")
  private String opt_size;

  private String event_name ="";
}

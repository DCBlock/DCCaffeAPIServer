package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Purchase2Vo {

  private final int code;

  private final int price;

  @JsonProperty("dc_price")
  private final int dcPrice;

  @NonNull
  @JsonProperty("type")
  private String optType;

  @NonNull
  @JsonProperty("size")
  private String optSize;

  private final int count;

  @NonNull
  @JsonProperty("menu_name_kr")
  private String menuNameKr;

  @JsonProperty("purchase_type")
  private final int purchaseType;

  @JsonIgnore
  private Timestamp purchaseDate;

  @JsonIgnore
  private String name;

  @JsonIgnore
  private long userRecordIndex;

  @JsonIgnore
  private int receiptId;
}

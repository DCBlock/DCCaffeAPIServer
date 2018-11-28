package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PurchaseVo {

    @NonNull
    private int code;

    @NonNull
    private int price;

    @NonNull
    @JsonProperty("dc_price")
    private int dcPrice;

    @NonNull
    @JsonProperty("opt_type")
    private int optType;

    @NonNull
    @JsonProperty("opt_size")
    private int optSize;

    @NonNull
    private int count;

    @NonNull
    @JsonProperty("menu_name_kr")
    private String menuNameKr;

    @JsonIgnore
    private Timestamp purchaseDate;

    @JsonIgnore
    private String name;

    @JsonIgnore
    private long userRecordIndex;

    @JsonIgnore
    private int receiptId;
}

package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class Purchase2Dto {

    private int category;

    private int code;

    private int price;

    private int dc_price;

    @NonNull
    private String type;

    @NonNull
    private String size;

    private int count;

    @JsonIgnore
    @NonNull
    private String menu_name_kr;

    @JsonIgnore
    private String name;

    @JsonIgnore
    private long user_record_index;

    @JsonIgnore
    private int receipt_id;

    @JsonIgnore
    private int receipt_status;
}

package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class PurchaseDto {

    private int category;

    private int code;

    private int price;

    private int dc_price;

    private int opt_type;

    private int opt_size;

    private int count;

    @NonNull
    private String menu_name_kr;

    @JsonIgnore
    private String name;

    private long user_record_index;

    @JsonIgnore
    private int receipt_id;

    private int receipt_status;
}

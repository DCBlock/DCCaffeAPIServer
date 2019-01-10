package com.digicap.dcblock.caffeapiserver.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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

    private int purchase_type;

    @JsonProperty(access = Access.WRITE_ONLY)
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)
    private String company;

    private Timestamp purchase_date;
}

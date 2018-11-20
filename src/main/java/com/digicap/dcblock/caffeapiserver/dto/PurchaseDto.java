package com.digicap.dcblock.caffeapiserver.dto;

import lombok.Getter;
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

    private String name;

    private long user_record_index;

    private String menu_name_kr;

    private int receipt_id;
}

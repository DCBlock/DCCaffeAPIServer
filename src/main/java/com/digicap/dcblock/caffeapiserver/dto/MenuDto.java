package com.digicap.dcblock.caffeapiserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MenuDto {

    int category;

    int code;

    String name_kr;

    String name_en;

    int price;

    int dc_digicap;

    int dc_covision;

    int opt_type;

    int opt_size;
}

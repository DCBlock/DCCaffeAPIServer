package com.digicap.dcblock.caffeapiserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MenuDto {

    private int category;

    private int code;

    private int order = -1;

    private String name_kr;

    private String name_en;

    private int price = -1;

    private int dc_digicap = -1;

    private int dc_covision = -1;

    private int opt_type = -1;

    private int opt_size = -1;
}

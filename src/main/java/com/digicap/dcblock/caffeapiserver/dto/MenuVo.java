package com.digicap.dcblock.caffeapiserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MenuVo {

    private int category;

    private int code;

    private String name_kr;

    private String name_en;

    private int price;

    private int dc_digicap;

    private int dc_covision;

    private int opt_type;

    private int opt_size;
}

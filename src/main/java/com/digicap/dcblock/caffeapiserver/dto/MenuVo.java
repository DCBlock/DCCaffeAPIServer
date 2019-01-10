package com.digicap.dcblock.caffeapiserver.dto;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MenuVo {

    private int category;

    private int code;

    private int order;

    private String name_en;

    private String name_kr;

    private int price = -1;

    private int dc_digicap = -1;

    private int dc_covision = -1;

    private int opt_size = -1;

    private int opt_type = -1;

    private String event_name;
    
    private long index;
    
    private HashMap<String, Integer> discounts;
}

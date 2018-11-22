package com.digicap.dcblock.caffeapiserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MenuExcludeOrderDto {

    int category;

    int code;

    String name_ne;
    
    String name_kr;

    int price;
    
    int dc_digicap;
    
    int dc_covision;
    
    int opt_size;
    
    int opt_type;
}

package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Setter
@Getter
@ToString
public class MenuDto {

    @JsonProperty(access = Access.WRITE_ONLY)
    private int order = -1;

    private int category;
    private int code;

    private String name_kr;
    private String name_en;

    private int price = -1;

    private int dc_digicap = -1;
    private int dc_covision = -1;

    @JsonProperty("type")
    private String opt_type;

    @JsonProperty("size")
    private String opt_size;

    private String event_name = "";

    private HashMap<String, Integer> discounts;

//    public MenuDto() {
//        discounts = new HashMap<>();
//        discounts.put("digicap", 5000);
//        discounts.put("covision", 3000);
//        discounts.put("design", 1000);
//    }
}

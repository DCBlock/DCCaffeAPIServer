package com.digicap.dcblock.caffeapiserver.dto;

import com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Optional;

@Setter
@Getter
@ToString
@JsonPropertyOrder({"category", "code", "name_kr", "name_en", "price", "type", "size", "event_name", "discounts"})
public class MenuDto implements CaffeApiServerApplicationConstants {

    @JsonProperty(access = Access.WRITE_ONLY)
    private int order = -1;

    private int category;
    private int code;
    private String name_en;
    private String name_kr;
    private int price;

    @JsonProperty("type")
    private String optType;
    
    @JsonProperty("size")
    private String optSize;

    private String event_name = "";

    private HashMap<String, Integer> discounts;

    public MenuDto(MenuVo v) {
        this.category = v.getCategory();
        this.code = v.getCode();
        this.order = v.getOrder();
        this.name_en = v.getName_en();
        this.name_kr = v.getName_en();
        this.price = v.getPrice();
        this.event_name = Optional.ofNullable(v.getEvent_name()).orElse("");

        switch (v.getOpt_type()) {
        case 0:
            this.optType = OPT_TYPE_HOT;
            break;
        case 1:
            this.optType = OPT_TYPE_ICED;
            break;
        case 2:
            this.optType = OPT_TYPE_BOTH;
            break;
        }

        switch (v.getOpt_size()) {
        case 0:
            this.optSize = OPT_SIZE_REGULAR;
            break;
        case 1:
            this.optSize = OPT_SIZE_SMALL;
            break;
        }
    }
}

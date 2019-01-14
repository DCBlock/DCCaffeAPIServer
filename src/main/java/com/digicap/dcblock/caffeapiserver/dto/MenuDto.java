package com.digicap.dcblock.caffeapiserver.dto;

import java.util.HashMap;
import java.util.Optional;

import com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants;
import com.digicap.dcblock.caffeapiserver.type.OptSize;
import com.digicap.dcblock.caffeapiserver.type.OptType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
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
    private OptType optType;
    
    @JsonProperty("size")
    private OptSize optSize;
    
    private String event_name = "";

    private HashMap<String, Integer> discounts;

    public MenuDto(MenuVo v) {
        this.category = v.getCategory();
        this.code = v.getCode();
        this.order = v.getOrder();
        this.name_en = v.getName_en();
        this.name_kr = v.getName_kr();
        this.price = v.getPrice();
        this.event_name = Optional.ofNullable(v.getEvent_name()).orElse("");
        this.optSize = v.getOpt_size();
        this.optType = v.getOpt_type();
    }
}

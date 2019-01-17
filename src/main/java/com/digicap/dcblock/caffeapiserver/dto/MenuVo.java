package com.digicap.dcblock.caffeapiserver.dto;

import java.util.HashMap;

import com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants;
import com.digicap.dcblock.caffeapiserver.type.OptSize;
import com.digicap.dcblock.caffeapiserver.type.OptType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class MenuVo implements CaffeApiServerApplicationConstants {

    private int category;

    private int code;

    private int order;

    private String name_en;

    private String name_kr;

    private int price = -1;

    private OptSize opt_size;

    private OptType opt_type;

    private String event_name;

    private long index;

    private HashMap<String, Integer> discounts;

    // -----------------------------------------------------------------------
    // Constructor
    
    public MenuVo(MenuDto m) {
        this.category = m.getCategory();
        this.code = m.getCode();
        this.order = m.getOrder();
        this.name_en = m.getName_en();
        this.name_kr = m.getName_kr();
        this.price = m.getPrice();
        this.event_name = m.getEvent_name();
        this.opt_size = m.getOptSize();
        this.opt_type = m.getOptType();
        
        // Set discounts
        if (m.getDiscounts() != null) {
            this.discounts = m.getDiscounts();
        } else {
            this.discounts = new HashMap<>();
        }
    }
}

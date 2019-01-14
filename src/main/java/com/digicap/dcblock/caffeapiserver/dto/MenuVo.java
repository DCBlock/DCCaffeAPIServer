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

//    private int opt_size = -1;

    private OptSize opt_size;

//    private int opt_type = -1;

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

//        switch (m.getOptType()) {
//        case OPT_TYPE_HOT:
//            this.opt_type = 0;
//            break;
//        case OPT_TYPE_ICED:
//            this.opt_type = 1;
//            break;
//        case OPT_TYPE_BOTH:
//            this.opt_type = 2;
//            break;
//        default:
//            throw new InvalidParameterException(String.format("unknown opt_type value(%s)", m.getOptType()));
//        }
//
//        switch (m.getOptSize()) {
//        case OPT_SIZE_REGULAR:
//            this.opt_size = 0;
//            break;
//        case OPT_SIZE_SMALL:
//            this.opt_size = 1;
//            break;
//        default:
//            throw new InvalidParameterException(String.format("unknown opt_size value(%s)", m.getOptSize()));
//        }
    }
}

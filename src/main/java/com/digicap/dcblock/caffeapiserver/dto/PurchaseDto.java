package com.digicap.dcblock.caffeapiserver.dto;

import java.sql.Timestamp;
import java.util.Optional;

import com.digicap.dcblock.caffeapiserver.type.OptSize;
import com.digicap.dcblock.caffeapiserver.type.OptType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PurchaseDto {

    private int category;

    private int code;

    private int price;

    private int dc_price;

    private OptType opt_type;

    private OptSize opt_size;

    private int count;

    @NonNull
    private String menu_name_kr;

    @JsonIgnore
    private String name;

    private long user_record_index;

    @JsonIgnore
    private int receipt_id;

    private int receipt_status;

    private int purchase_type;
    
    @JsonProperty(access = Access.WRITE_ONLY)
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)
    private String company;

    private Timestamp purchase_date;
    
    public PurchaseDto(PurchaseVo v) {
        this.category = v.getCategory();
        this.code = v.getCode();
        this.price = v.getPrice();
        this.dc_price = v.getDc_price();
        this.opt_type = v.getOpt_type();
        this.opt_size = v.getOpt_size();
        this.count = v.getCount();
        this.menu_name_kr = Optional.ofNullable(v.getMenu_name_kr()).orElse("");
        this.user_record_index = v.getUser_record_index();
        this.receipt_status = v.getReceipt_status();
        this.purchase_type = v.getPurchase_type().ordinal();
        this.purchase_date = v.getPurchase_date(); 

//        private String email;
//        private String company;
    }
}

package com.digicap.dcblock.caffeapiserver.dto;

import com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants;
import com.digicap.dcblock.caffeapiserver.type.OptSize;
import com.digicap.dcblock.caffeapiserver.type.OptType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class PurchasePartialDto implements CaffeApiServerApplicationConstants {

    private int category;

    private int code;

    private int price;

    @NonNull
    private OptType type;

    @NonNull
    private OptSize size;

    private int count;

    @JsonIgnore
    @NonNull
    private String menu_name_kr;

    @JsonIgnore
    private String name;

    @JsonIgnore
    private long user_record_index;

    @JsonIgnore
    private int receipt_id;

    @JsonIgnore
    private int receipt_status;
    
    public PurchasePartialDto(PurchaseVo p) {
        this.category = p.getCategory();
        this.code = p.getCode();
        this.count = p.getCount();
        this.menu_name_kr = p.getMenu_name_kr();
        this.name = p.getName();
        this.price = p.getPrice();
        this.receipt_id = p.getReceipt_id();
        this.receipt_status = p.getReceipt_status();
        this.user_record_index = p.getUser_record_index();
        this.type = p.getOpt_type();
        this.size = p.getOpt_size();
    }
    
    public PurchasePartialDto(PurchaseDto p) {
        this.category = p.getCategory();
        this.code = p.getCode();
        this.count = p.getCount();
        this.menu_name_kr = p.getMenu_name_kr();
        this.name = p.getName();
        this.price = p.getPrice();
        this.receipt_id = p.getReceipt_id();
        this.receipt_status = p.getReceipt_status();
        this.user_record_index = p.getUser_record_index();
        this.type = p.getOpt_type();
        this.size = p.getOpt_size();
    }
}

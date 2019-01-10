package com.digicap.dcblock.caffeapiserver.dto;

import com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class Purchase2Dto implements CaffeApiServerApplicationConstants {

    private int category;

    private int code;

    private int price;

    private int dc_price;

    @NonNull
    private String type;

    @NonNull
    private String size;

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
    
    public Purchase2Dto(PurchaseDto purchaseDto) {
        this.category = purchaseDto.getCategory();
        this.code = purchaseDto.getCode();
        this.count = purchaseDto.getCount();
        this.dc_price = purchaseDto.getDc_price();
        this.menu_name_kr = purchaseDto.getMenu_name_kr();
        this.name = purchaseDto.getName();
        this.price = purchaseDto.getPrice();
        this.receipt_id = purchaseDto.getReceipt_id();
        this.receipt_status = purchaseDto.getReceipt_status();
        this.user_record_index = purchaseDto.getUser_record_index();

        switch (purchaseDto.getOpt_size()) {
            case 0:
                this.size = OPT_SIZE_REGULAR;
                break;
            case 1:
                this.size = OPT_SIZE_SMALL;
                break;
        }

        switch (purchaseDto.getOpt_type()) {
            case 0:
                this.type = OPT_TYPE_HOT;
                break;
            case 1:
                this.type = OPT_TYPE_ICED;
                break;
            case 2:
                this.type = OPT_TYPE_BOTH;
                break;
        }
    }
}

package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class PurchaseSearchDto {

    @NonNull
    private String menu_name_kr;

    private int price;

    private int dc_price;

    @NonNull
    private String type;

    @NonNull
    private String size;

    private int count;

    private int receipt_status;

    private long purchase_date;

    private long cancel_date;
}

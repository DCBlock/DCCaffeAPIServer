package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class PurchaseNewDto {

    private int count;
    private int price;
    private int dc_price;
    private String name;
    private long user_record_index;
    private int code;
    @NonNull
    private String menu_name_kr;
    private int receipt_status;
    private int opt_size;
    private int opt_type;
    private Timestamp update_date;
    private Timestamp cancel_date;
    private Timestamp purchase_date;
    private Timestamp canceled_date;
    private long index;
    private int receipt_id;
}

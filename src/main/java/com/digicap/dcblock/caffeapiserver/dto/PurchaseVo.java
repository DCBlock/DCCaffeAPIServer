package com.digicap.dcblock.caffeapiserver.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseVo {

    private int count;

    private int price;

    private int dc_price;

    private String name;

    private long user_record_index;

    private int code;

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

    private int category;

    private int purchase_type;

    private String email;

    private String company;
}

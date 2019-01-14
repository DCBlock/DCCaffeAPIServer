package com.digicap.dcblock.caffeapiserver.dto;

import java.sql.Timestamp;

import com.digicap.dcblock.caffeapiserver.type.OptSize;
import com.digicap.dcblock.caffeapiserver.type.OptType;
import com.digicap.dcblock.caffeapiserver.type.PurchaseType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseVo {

    private int category;

    private int code;

    private int price;
    
    private int dc_price;
    
    private OptType opt_type;
    
    private OptSize opt_size;

    private int count;

    private String menu_name_kr;

    private String name;

    private long user_record_index;

    private int receipt_id;

    private int receipt_status;

    private PurchaseType purchase_type;

    private String email;

    private String company;

    private Timestamp purchase_date;


    private Timestamp update_date;

    private Timestamp cancel_date;

    private Timestamp canceled_date;

    private long index;
}

package com.digicap.dcblock.caffeapiserver.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReceiptIdDto {

    private String receipt_id;

    private String name;

    private String company;
    
    private String date;
}

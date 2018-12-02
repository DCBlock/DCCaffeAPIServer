package com.digicap.dcblock.caffeapiserver.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseBalanceDto {

    private String name;

    private int total_price;

    private int total_dc_price;
}

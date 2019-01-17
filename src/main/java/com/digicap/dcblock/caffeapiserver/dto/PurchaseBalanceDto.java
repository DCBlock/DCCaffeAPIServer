package com.digicap.dcblock.caffeapiserver.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseBalanceDto {

    private String name;

    private long total_price;

    private long total_dc_price;
}

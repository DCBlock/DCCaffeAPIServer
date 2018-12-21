package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;

@Setter
@Getter
@ToString
@JsonPropertyOrder({"name", "total_price", "total_dc_price", "before_date", "after_date", "purchases"})
public class SettlementReportDto {

    private String name;

    @JsonProperty("total_price")
    private long totalPrice;

    @JsonProperty("total_dc_price")
    private long totalDcPrice;

    @JsonProperty("before_date")
    private long beforeDate;

    @JsonProperty("after_date")
    private long afterDate;

    private LinkedList<PurchaseSearchDto> purchases;
}

package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;

@Setter
@Getter
@ToString
@JsonPropertyOrder({"name", "total_price", "total_dc_price", "purchases"})
public class SettlementUserReportDto {

    private String name;

    @JsonProperty("total_price")
    private long totalPrice;

    @JsonProperty("total_dc_price")
    private long totalDcPrice;

    @JsonInclude
    @JsonProperty("before_date")
    private long beforeDate;

    @JsonInclude
    @JsonProperty("after_date")
    private long afterDate;

    private LinkedList<PurchaseSearchDto> purchases;
}

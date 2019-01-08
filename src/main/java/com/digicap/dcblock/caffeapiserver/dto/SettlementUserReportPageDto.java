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
@JsonPropertyOrder({"name", "total_price", "total_dc_price", "total_pages", "purchases"})
public class SettlementUserReportPageDto {

    private String name;

    @JsonProperty("total_price")
    private long totalPrice;

    @JsonProperty("total_dc_price")
    private long totalDcPrice;

    @JsonProperty("total_pages")
    private int totalPages;

    private LinkedList<PurchaseSearchDto> purchases;
}

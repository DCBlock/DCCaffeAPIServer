package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SettlementReportGuestsDto {

    @JsonProperty("name")
    private String name = "";

    @JsonProperty("email")
    private String email = "";

    @JsonProperty("total_price")
    private long totalPrice;

    @JsonProperty("total_purchase_price")
    private long totalPurchasePrice;

    @JsonProperty("total_cancel_price")
    private long totalCancelPrice;

    @JsonProperty("total_canceled_price")
    private long totalCanceledPrice;

    @JsonProperty("total_count")
    private int totalCount;

    @JsonProperty("total_purchase_count")
    private int totalPurchaseCount;

    @JsonProperty("total_cancel_count")
    private int totalCancelCount;

    @JsonProperty("total_canceled_count")
    private int totalCanceledCount;
}

package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PurchaseCancelingDto {

    @JsonProperty("receipt_id")
    private int receiptId;

    @JsonProperty("purchased_date")
    private String purchasedDate;

    @JsonProperty("purchase_cancels")
    private List<PurchasePartialDto> purchaseCancels;
}

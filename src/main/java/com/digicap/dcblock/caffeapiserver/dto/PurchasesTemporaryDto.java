package com.digicap.dcblock.caffeapiserver.dto;

import java.util.Optional;

import com.digicap.dcblock.caffeapiserver.type.OptSize;
import com.digicap.dcblock.caffeapiserver.type.OptType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class PurchasesTemporaryDto {

    private final int code;

    private final long price;

    private final long dc_price;

    @NonNull
    @JsonProperty("type")
    private OptType optType;

    @NonNull
    @JsonProperty("size")
    private OptSize optSize;

    private final int count;

    @NonNull
    @JsonProperty("menu_name_kr")
    private String menuNameKr;

    @JsonProperty("purchase_type")
    private final int purchaseType;

    @JsonProperty("purchase_date")
    private long purchaseDate;

    @JsonProperty("cancel_date")
    private long cancelDate;

    @JsonProperty("canceled_date")
    private long canceledDate;

    @JsonProperty("receipt_status")
    private int receiptStatus;

    public PurchasesTemporaryDto(PurchaseVo p) {
        this.code = p.getCode();
        this.price = p.getPrice();
        this.dc_price = p.getDc_price();
        this.count = p.getCount();
        this.receiptStatus = p.getReceipt_status();
        this.menuNameKr = Optional.ofNullable(p.getMenu_name_kr()).orElse("");
        this.purchaseDate = p.getPurchase_date().getTime() / 1_000;

        if (p.getCancel_date() == null) {
            this.cancelDate = 0;
        } else {
            this.cancelDate = p.getCancel_date().getTime() / 1_000;
        }

        if (p.getCanceled_date() == null) {
            this.canceledDate = 0;
        } else {
            this.canceledDate = p.getCanceled_date().getTime() / 1_000;
        }

        this.purchaseType = p.getPurchase_type().ordinal();
        this.optSize = p.getOpt_size();
        this.optType = p.getOpt_type();
    }
}

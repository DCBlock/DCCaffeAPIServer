package com.digicap.dcblock.caffeapiserver.dto;

import com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class PurchasesTemporaryDto implements CaffeApiServerApplicationConstants {

    private final int code;

    private final long price;

    private final long dc_price;

    @NonNull
    @JsonProperty("type")
    private String optType;

    @NonNull
    @JsonProperty("size")
    private String optSize;

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

    public PurchasesTemporaryDto(PurchaseNewDto p) {
        this.code = p.getCode();
        this.price = p.getPrice();
        this.dc_price = p.getDc_price();
        this.count = p.getCount();
        this.receiptStatus = p.getReceipt_status();
        this.purchaseType = p.getPurchase_type();
        this.menuNameKr = p.getMenu_name_kr();
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

        switch (p.getOpt_size()) {
            case 0:
                this.optSize = OPT_SIZE_REGULAR;
                break;
            case 1:
                this.optSize = OPT_SIZE_SMALL;
                break;
            default:
                throw new InvalidParameterException(String.format("unknown opt_size(%s)", p.getOpt_size()));
        }

        switch (p.getOpt_type()) {
            case 0:
                this.optType = OPT_TYPE_HOT;
                break;
            case 1:
                this.optType = OPT_TYPE_ICED;
                break;
            case 2:
                this.optType = OPT_TYPE_BOTH;
                break;
            default:
                throw new InvalidParameterException(String.format("unknown opt_type(%s)", p.getOpt_type()));
        }
    }
}

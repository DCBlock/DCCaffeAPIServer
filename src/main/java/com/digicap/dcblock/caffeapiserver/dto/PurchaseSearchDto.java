package com.digicap.dcblock.caffeapiserver.dto;

import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.Timestamp;

import static com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants.*;

@Setter
@Getter
public class PurchaseSearchDto {

    private long date;

    private String receipt_id;

    @NonNull
    private String menu_name_kr;

    private int price;

    private int dc_price;

    @NonNull
    private String type;

    @NonNull
    private String size;

    private int count;

    private int receipt_status;

    private long purchase_date;

    private long cancel_date;

    public PurchaseSearchDto(PurchaseNewDto p) {
        this.date = p.getUpdate_date().getTime();
        this.receipt_id = insertZeroString(p.getReceipt_id());

        this.menu_name_kr = p.getMenu_name_kr();
        this.price = p.getPrice();
        this.dc_price = p.getDc_price();
        this.count = p.getCount();
        this.receipt_status = p.getReceipt_status();
        this.purchase_date = p.getPurchase_date().getTime();
        this.cancel_date = p.getCancel_date().getTime();

        switch (p.getOpt_size()) {
            case 0:
                this.size = OPT_SIZE_REGULAR;
                break;
            case 1:
                this.size = OPT_SIZE_SMALL;
                break;
            default:
                throw new InvalidParameterException(String.format("unknown opt_size(%s)", p.getOpt_size()));
        }

        switch (p.getOpt_type()) {
            case 0:
                this.type = OPT_TYPE_HOT;
                break;
            case 1:
                this.type = OPT_TYPE_ICED;
                break;
            case 2:
                this.type = OPT_TYPE_BOTH;
                break;
            default:
                throw new InvalidParameterException(String.format("unknown opt_type(%s)", p.getOpt_type()));
        }
    }

    private String insertZeroString(int number) {
        int length = String.valueOf(number).length();

        String value = String.valueOf(number);

        if (length == 3) {
            return "0" + value;
        } else if (length == 2) {
            return "00" + value;
        } else if (length == 1) {
            return "000" + value;
        } else {
            return value;
        }
    }
}

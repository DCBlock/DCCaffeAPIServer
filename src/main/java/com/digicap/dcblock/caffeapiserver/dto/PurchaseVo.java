package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@ToString
public class PurchaseVo {

    private int code;

    private int price;

    private int dcPrice;

    private int optType;

    private int optSize;

    private int count;

    private String menuNameKr;

    private int purchaseType;

    private Timestamp purchaseDate;

    private String name;

    private long userRecordIndex;

    private int receiptId;
}

package com.digicap.dcblock.caffeapiserver.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PurchaseVo {

    @NonNull
    private int category;

    @NonNull
    private int code;

    @NonNull
    private int price;

    @NonNull
    private int dcPrice;

    @NonNull
    private int optType;

    @NonNull
    private int optSize;

    @NonNull
    private int count;

    @NonNull
    private String menuNameKr;

    private String purchaseDate;

    private String name;

    private long userRecordIndex;

    private int receiptId;
}

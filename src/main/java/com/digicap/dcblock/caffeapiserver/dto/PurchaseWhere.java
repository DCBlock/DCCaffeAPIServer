package com.digicap.dcblock.caffeapiserver.dto;

import java.sql.Timestamp;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PurchaseWhere {

    private Timestamp before;

    private Timestamp after;

    private int filter;

    private String company;

    private long userRecordIndex;

    private int perPage;

    private int page;

    private int purchaseType = -1;
}

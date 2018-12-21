package com.digicap.dcblock.caffeapiserver.dto;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReceiptIdVo {

    private String name;

    private Timestamp regdate;

    private long receiptId;

    private String randomId;

    private long userRecordIndex;

    private String company;
}

package com.digicap.dcblock.caffeapiserver.dto;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@ToString
public class ReceiptIdVo {

  private String name;

  private String company;

  private String email;

  private long userRecordIndex;

  private long receiptId;

  private String randomId;

  private Timestamp regdate;
}


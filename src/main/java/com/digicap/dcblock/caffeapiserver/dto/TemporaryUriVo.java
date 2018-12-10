package com.digicap.dcblock.caffeapiserver.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TemporaryUriVo {

    private String randomUri;

    private long userRecordIndex;

    private String name;

    private Timestamp regDate;

    private Timestamp searchDateAfter;

    private Timestamp searchDateBefore;
}

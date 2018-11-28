package com.digicap.dcblock.caffeapiserver.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class TemporaryUriVo {

    private String randomUri;

    private long userRecordIndex;

    private String name;

    private Timestamp regDate;
}

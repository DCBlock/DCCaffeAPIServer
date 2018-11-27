package com.digicap.dcblock.caffeapiserver.dto;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class TemporaryUriVo {

    private String randomUri;

    private String name;

    private long userRecordIndex;

    private Date regDate;
}

package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
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

    @JsonProperty(access = Access.WRITE_ONLY)
    private Timestamp regDate;

    @JsonProperty(access = Access.WRITE_ONLY)
    private Timestamp searchDateAfter;

    @JsonProperty(access = Access.WRITE_ONLY)
    private Timestamp searchDateBefore;
}

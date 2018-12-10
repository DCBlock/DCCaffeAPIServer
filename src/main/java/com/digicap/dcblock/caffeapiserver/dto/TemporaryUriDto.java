package com.digicap.dcblock.caffeapiserver.dto;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TemporaryUriDto {

    private String random_uri;

    private String name;

    private long userRecordIndex;

    @JsonProperty(access = Access.WRITE_ONLY)
    private Date regDate;

    @JsonProperty(access = Access.WRITE_ONLY)
    private Timestamp searchDateBefore;

    @JsonProperty(access = Access.WRITE_ONLY)
    private Timestamp searchDateAfter;
}

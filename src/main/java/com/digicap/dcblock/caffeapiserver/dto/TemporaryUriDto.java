package com.digicap.dcblock.caffeapiserver.dto;

import java.util.Date;
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

    private Date regDate;
}

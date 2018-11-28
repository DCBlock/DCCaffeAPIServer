package com.digicap.dcblock.caffeapiserver.dto;

import java.util.Date;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class TemporaryUriVo {

    @NonNull
    private String randomUri;

    @NonNull
    private long userRecordIndex;

    @NonNull
    private String name;

    private Date regDate;
}

package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReceiptIdVo {

    private String name;

    private String company;

    private long user_record_index;

    @JsonIgnoreProperties
    private Timestamp regdate;
}

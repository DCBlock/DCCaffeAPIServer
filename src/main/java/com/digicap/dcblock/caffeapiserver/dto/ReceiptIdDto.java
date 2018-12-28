package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReceiptIdDto {

    private String name;

    private String company;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    private String receipt_id;

    @JsonProperty("random_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String randomId;

    @JsonProperty(access = Access.WRITE_ONLY)
    private long userRecordIndex;

    private String date;
}

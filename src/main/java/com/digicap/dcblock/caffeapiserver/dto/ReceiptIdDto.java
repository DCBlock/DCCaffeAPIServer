package com.digicap.dcblock.caffeapiserver.dto;

import com.digicap.dcblock.caffeapiserver.util.TimeFormat;
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
    @JsonProperty(access = Access.WRITE_ONLY)
    private String email;

    @JsonProperty("receipt_id")
    private long receiptId;

    @JsonProperty("random_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String randomId;

    @JsonProperty(access = Access.WRITE_ONLY)
    private long userRecordIndex;

    private String date;
    
    //------------------------------------------------------------------------
    // Constructor
    
    public ReceiptIdDto(UserDto user) {
        this.name = user.getName();
        this.company = user.getCompany().toLowerCase();
        this.userRecordIndex = user.getIndex();
        this.email = user.getEmail();
        this.date = new TimeFormat().getCurrent();
    }
}

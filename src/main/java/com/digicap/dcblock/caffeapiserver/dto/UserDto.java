package com.digicap.dcblock.caffeapiserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDto {

    private int index;
    private String email;
    private String rfid;
    private String name;
    private String company;
    private boolean isLeave;
    private long regdate;
    private long updatedate;
}

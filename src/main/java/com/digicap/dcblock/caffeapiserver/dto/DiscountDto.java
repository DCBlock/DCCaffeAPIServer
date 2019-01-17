package com.digicap.dcblock.caffeapiserver.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * discount table DTO.
 * @author DigiCAP
 *
 */
@Setter
@Getter
@ToString
public class DiscountDto {

    private long menuIndex;

    private long discount;

    private String company;

    private Timestamp reg;
    
    private Timestamp update;
}

package com.digicap.dcblock.caffeapiserver.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * discount table VO.
 * @author DigiCAP
 *
 */
@Getter
@ToString
@AllArgsConstructor
public class DiscountVo {

    private long menuIndex;

    private long discount;

    private String company;

    private Timestamp reg;
    
    private Timestamp update;
}

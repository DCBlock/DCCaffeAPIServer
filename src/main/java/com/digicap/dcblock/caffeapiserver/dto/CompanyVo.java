package com.digicap.dcblock.caffeapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

/**
 * Company Table
 */
@Getter
@AllArgsConstructor
public class CompanyVo {

    private int index;

    private String nameEng;

    private String nameKor;

    private Timestamp regDate;

    private Timestamp updateDate;
}

package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * Company Dto
 */
@Setter
@Getter
@ToString
public class CompanyDto {

    @JsonIgnore
    private int index;

    @JsonProperty("name_en")
    private String nameEng;

    @JsonProperty("name_kr")
    private String nameKor;

    @JsonIgnore
    private Timestamp regDate;

    @JsonIgnore
    private Timestamp updateDate;

    // -------------------------------------------------------------------------
    // Constructor

    public CompanyDto(CompanyVo vo) {
        this.index = vo.getIndex();
        this.nameEng = vo.getNameEng();
        this.nameKor = vo.getNameKor();
        this.regDate = vo.getRegDate();
        this.updateDate = vo.getUpdateDate();
    }
}

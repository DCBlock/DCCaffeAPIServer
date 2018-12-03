package com.digicap.dcblock.caffeapiserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * category table VO.
 */
@Getter
@ToString
public class CategoryVo {

    private String name;

    private int code;

    private int order;
}

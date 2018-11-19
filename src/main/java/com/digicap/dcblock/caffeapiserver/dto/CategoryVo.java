package com.digicap.dcblock.caffeapiserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * category table DAO.
 */
@Setter
@Getter
@ToString
public class CategoryVo {

    private String name;

    private int order;

    private int code;
}

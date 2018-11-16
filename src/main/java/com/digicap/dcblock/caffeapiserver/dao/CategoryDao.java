package com.digicap.dcblock.caffeapiserver.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * category table DAO.
 */
@Setter
@Getter
@ToString
public class CategoryDao {

    private String name;

    private int no;

    private int code;
}

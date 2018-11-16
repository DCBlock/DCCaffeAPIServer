package com.digicap.dcblock.caffeapiserver.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MenuDao {

    private int category;

    private int code;

    private String name_kor;

    private String name_eng;

    private int price;

    private int dc_digicap;

    private int dc_covision;

    private int opt_type;

    private int opt_size;
}

package com.digicap.dcblock.caffeapiserver.dto;

import java.util.LinkedList;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * category and menu dto.
 */
@Setter
@Getter
@ToString
public class MenusInCategoryDto {

    private String name;

    private int code;

    private int order;

    private LinkedList<MenuVo> menus;
}

package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.MenuVo;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.digicap.dcblock.caffeapiserver.dto.MenuDto;

public interface MenuService {

    LinkedHashMap<String, LinkedList<MenuDto>> getAllMenus();

    LinkedHashMap<Integer, LinkedList<MenuDto>> getAllMenusUsingCode();

    void deleteMenu(int category, int code);

    MenuVo setMenu(MenuVo menuVo);

    LinkedList<MenuDto> updateAllMenusInCategory(int category, LinkedList<MenuDto> menus);
}

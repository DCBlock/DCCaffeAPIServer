package com.digicap.dcblock.caffeapiserver.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.digicap.dcblock.caffeapiserver.dto.MenuDto;
import com.digicap.dcblock.caffeapiserver.dto.MenuVo;

public interface MenuService {

    /**
     * menus를 추가
     * @param menuVo
     * @return
     */
    MenuVo setMenu(MenuDto menuVo);

    LinkedHashMap<String, LinkedList<MenuDto>> getAllMenus();

    LinkedHashMap<Integer, LinkedList<MenuDto>> getAllMenusUsingCode();

    void deleteMenu(int category, int code);


    LinkedList<MenuDto> updateAllMenusInCategory(int category, LinkedList<MenuDto> menus);
}

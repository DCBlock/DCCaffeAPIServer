package com.digicap.dcblock.caffeapiserver.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.digicap.dcblock.caffeapiserver.dto.MenuDto;

public interface MenuService {

  LinkedHashMap<String, LinkedList<MenuDto>> getAllMenus();

  LinkedHashMap<Integer, LinkedList<MenuDto>> getAllMenusUsingCode();

  void deleteMenu(int category, int code);

  MenuDto setMenu(MenuDto menuVo);

  LinkedList<MenuDto> updateAllMenusInCategory(int category, LinkedList<MenuDto> menus);
}

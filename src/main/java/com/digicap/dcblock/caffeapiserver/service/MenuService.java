package com.digicap.dcblock.caffeapiserver.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.digicap.dcblock.caffeapiserver.dto.MenuDto;

public interface MenuService {

    public LinkedHashMap<String, LinkedList<MenuDto>> getAllMenus();

    public LinkedHashMap<Integer, LinkedList<MenuDto>> getAllMenusUsingCode();

    public void deleteMenu(int category, int code);
}

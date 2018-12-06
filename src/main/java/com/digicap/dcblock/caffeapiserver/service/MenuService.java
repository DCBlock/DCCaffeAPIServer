package com.digicap.dcblock.caffeapiserver.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.mybatis.spring.MyBatisSystemException;

import com.digicap.dcblock.caffeapiserver.dto.MenuDto;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;

public interface MenuService {

    LinkedHashMap<String, LinkedList<MenuDto>> getAllMenus() throws MyBatisSystemException, NotFindException, UnknownException;

    LinkedHashMap<Integer, LinkedList<MenuDto>> getAllMenusUsingCode() throws MyBatisSystemException, NotFindException, UnknownException;

    void deleteMenu(int category, int code) throws MyBatisSystemException, NotFindException;

    MenuDto setMenu(MenuDto menuVo) throws MyBatisSystemException, NotFindException;

    LinkedList<MenuDto> updateAllMenusInCategory(int category, LinkedList<MenuDto> menus) throws MyBatisSystemException, InvalidParameterException;
}

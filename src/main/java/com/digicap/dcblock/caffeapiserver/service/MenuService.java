package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.MenuVo;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import org.apache.ibatis.annotations.Select;

public interface MenuService {

    public LinkedHashMap<String, LinkedList<MenuVo>> getAllMenus();

    public LinkedHashMap<Integer, LinkedList<MenuVo>> getAllMenusUsingCode();
}

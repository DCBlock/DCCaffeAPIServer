package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dao.MenuDao;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public interface MenuService {

    public LinkedHashMap<String, LinkedList<MenuDao>> getAllMenus();
}

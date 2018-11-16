package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dao.MenuDao;
import com.digicap.dcblock.caffeapiserver.store.MenuMapper;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    MenuMapper mapper;

    @Autowired
    public MenuService(MenuMapper mapper) {
        this.mapper = mapper;
    }

    public LinkedList<MenuDao> getAllMenus() throws Exception {
        LinkedList<MenuDao> menus = null;


        return menus;
    }
}

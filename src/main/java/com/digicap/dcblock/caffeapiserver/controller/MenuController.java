package com.digicap.dcblock.caffeapiserver.controller;

import com.digicap.dcblock.caffeapiserver.dao.MenuDao;
import com.digicap.dcblock.caffeapiserver.service.MenuService;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/caffe/menus")
public class MenuController {

    private MenuService service;

    @Autowired
    public MenuController(MenuService service) {
        this.service = service;
    }

    @GetMapping
    LinkedHashMap<String, LinkedList<MenuDao>> getAllMenus() {
        LinkedHashMap<String, LinkedList<MenuDao>> menus;

        try {
            menus = service.getAllMenus();
        } catch (Exception e) {
            throw new UnknownError(e.getMessage());
        }

        return menus;
    }

//    @PostMapping("/api/menus")
//    String insertMenu() {
//        return "";
//    }
//
//    @PatchMapping("/api/menus/{code}")
//    String updateMenu(String body) {
//        return "";
//    }
//
//    @DeleteMapping("/api/menus/{code}")
//    String deleteMenu() {
//        return "";
//    }
}

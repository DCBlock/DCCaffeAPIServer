package com.digicap.dcblock.caffeapiserver.controller;

import java.util.LinkedList;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    @GetMapping("/api/menus")
    LinkedList<Object> getAllMenus() {
        return null;
    }

    @PostMapping("/api/menus")
    String insertMenu() {
        return "";
    }

    @PatchMapping("/api/menus/{code}")
    String updateMenu(String body) {
        return "";
    }

    @DeleteMapping("/api/menus/{code}")
    String deleteMenu() {
        return "";
    }
}

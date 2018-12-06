package com.digicap.dcblock.caffeapiserver.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.digicap.dcblock.caffeapiserver.dto.MenuDto;
import com.digicap.dcblock.caffeapiserver.service.MenuService;

/**
 * 카페에서 사용하는 메뉴 Controller Class.
 * 
 * @author DigiCAP
 */
@RestController
public class MenuController {

    private MenuService service;

    @Autowired
    public MenuController(MenuService service) {
        this.service = service;
    }

//    @ApiOperation(value = "/api/caffe/menus", nickname = "if-caffe-pub-002")
//    @ApiResponses(value = {
//        @ApiResponse(code = 200, message = "Success", response = LinkedHashMap.class),
//        @ApiResponse(code = 500, message = "Failure", response = ApiError.class)})
    @GetMapping("/api/caffe/menus")
    LinkedHashMap<String, LinkedList<MenuDto>> getAllMenus() throws MyBatisSystemException {
        LinkedHashMap<String, LinkedList<MenuDto>> menus = service.getAllMenus();
        return menus;
    }

    @PostMapping("/api/caffe/menus")
    MenuDto insertMenu(@RequestBody MenuDto body) {
        MenuDto result = service.setMenu(body);
        return result;
    }

    @PatchMapping("/api/caffe/menus/{category}")
    LinkedList<MenuDto> updateMenuInCategory(@PathVariable("category") int category,@RequestBody LinkedList<MenuDto> body) {
        return service.updateAllMenusInCategory(category, body);
    }

    @DeleteMapping("/api/caffe/menus/{category}/{code}")
    HashMap<String, String> deleteMenu(@PathVariable("category") int category, @PathVariable("code") int code) {
        service.deleteMenu(category, code);
        return new HashMap<>();
    }
}

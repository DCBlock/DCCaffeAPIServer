package com.digicap.dcblock.caffeapiserver.controller;

import com.digicap.dcblock.caffeapiserver.dto.ApiError;
import com.digicap.dcblock.caffeapiserver.dto.MenuVo;
import com.digicap.dcblock.caffeapiserver.service.MenuService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "/api/caffe/menus", nickname = "if-caffe-pub-002")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = LinkedHashMap.class),
        @ApiResponse(code = 500, message = "Failure", response = ApiError.class)})
    @GetMapping()
    LinkedHashMap<String, LinkedList<MenuVo>> getAllMenus() {
        LinkedHashMap<String, LinkedList<MenuVo>> menus;

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

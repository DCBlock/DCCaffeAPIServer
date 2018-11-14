package com.digicap.dcblock.caffeapiserver.category.controller;

import java.util.LinkedList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    @GetMapping("/api/menus")
    LinkedList<Object> getCategories() {
        return null;
    }
}

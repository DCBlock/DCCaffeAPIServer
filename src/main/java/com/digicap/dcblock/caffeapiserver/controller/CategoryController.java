package com.digicap.dcblock.caffeapiserver.controller;

import com.digicap.dcblock.caffeapiserver.dao.CategoryDao;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.service.CategoryService;
import java.util.LinkedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@Slf4j
public class CategoryController {

    @Autowired
    CategoryService service;

    @GetMapping
    LinkedList<CategoryDao> getAllCategory() throws NotFindException, UnknownException {
        LinkedList<CategoryDao> categoriesDao = null;

        try {
            categoriesDao = service.getAllCategories();
        } catch (NotFindException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new UnknownException(e.getMessage());
        }

        return categoriesDao;
    }
}

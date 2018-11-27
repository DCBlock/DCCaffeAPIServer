package com.digicap.dcblock.caffeapiserver.controller;

import com.digicap.dcblock.caffeapiserver.dto.CategoryVo;
import com.digicap.dcblock.caffeapiserver.dto.MenusInCategoryDto;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.service.CategoryService;
import java.util.LinkedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/caffe/categories")
@Slf4j
public class CategoryController {

    private CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    LinkedList<CategoryVo> getAllCategory() throws NotFindException, UnknownException {
        LinkedList<CategoryVo> categoriesDao = null;

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

    @PostMapping
    CategoryVo createCategory(@RequestBody CategoryVo categoryVo) {
        if (categoryVo.getName().replaceAll(" ", "").isEmpty()) {
            throw new InvalidParameterException("name is empty");
        }

        CategoryVo result = service.postCategory(categoryVo.getName());
        return result;
    }

    @DeleteMapping("/{code}")
    MenusInCategoryDto deleteCategory(@PathVariable("code") int code) {

        MenusInCategoryDto result = service.deleteCategory(code);
        return result;
    }
}

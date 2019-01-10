package com.digicap.dcblock.caffeapiserver.controller;

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

import com.digicap.dcblock.caffeapiserver.dto.CategoryVo;
import com.digicap.dcblock.caffeapiserver.dto.MenusInCategoryDto;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.service.CategoryService;
import com.google.common.base.Preconditions;

/**
 * 카페에서 사용하는 카테고리 관련 Controller Class
 *
 * @author DigiCAP
 */
@RestController
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/caffe/categories")
    LinkedList<CategoryVo> getAllCategory() {
        LinkedList<CategoryVo> categoriesDao = null;

        try {
            categoriesDao = categoryService.getAllCategories();
        } catch (MyBatisSystemException | NotFindException e) {
            throw e;
        } catch (Exception e) {
            throw new UnknownException(e.getMessage());
        }

        return categoriesDao;
    }

    @PostMapping(value = "/api/caffe/categories", consumes = "application/json; charset=utf-8")
    CategoryVo createCategory(@RequestBody CategoryVo categoryVo) {
        // Check Parameter.
        if (categoryVo.getName().replaceAll(" ", "").isEmpty()) {
            throw new InvalidParameterException("name is empty");
        }

        CategoryVo result = categoryService.postCategory(categoryVo.getName());
        return result;
    }

    @DeleteMapping("/api/caffe/categories/{code}")
    MenusInCategoryDto deleteCategory(@PathVariable("code") int code) {
        // Check Argument.
        Preconditions.checkArgument(code >= 0, "invalid code(%d)", code);

        MenusInCategoryDto result = categoryService.deleteCategory(code);
        return result;
    }

    @PatchMapping(value = "/api/caffe/categories", consumes = "application/json; charset=utf-8")
    void updateAllCategory(@RequestBody LinkedList<CategoryVo> categories) {
        categoryService.updateAll(categories);
    }
}

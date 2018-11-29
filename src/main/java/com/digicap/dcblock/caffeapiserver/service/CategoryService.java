package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.CategoryVo;
import com.digicap.dcblock.caffeapiserver.dto.MenusInCategoryDto;
import java.util.LinkedList;

public interface CategoryService {

    LinkedList<CategoryVo> getAllCategories() throws Exception;

    CategoryVo postCategory(String name);

    MenusInCategoryDto deleteCategory(int code);

    void updateAll(LinkedList<CategoryVo> categories);
}

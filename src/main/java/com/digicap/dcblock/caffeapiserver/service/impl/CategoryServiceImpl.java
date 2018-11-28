package com.digicap.dcblock.caffeapiserver.service.impl;

import com.digicap.dcblock.caffeapiserver.dto.CategoryVo;
import com.digicap.dcblock.caffeapiserver.dto.MenuVo;
import com.digicap.dcblock.caffeapiserver.dto.MenusInCategoryDto;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.service.CategoryService;
import com.digicap.dcblock.caffeapiserver.store.CategoryMapper;
import com.digicap.dcblock.caffeapiserver.store.MenuMapper;
import java.util.LinkedList;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private CategoryMapper categoryMapper;

    private MenuMapper menuMapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper mapper, MenuMapper menuMapper) {
        this.categoryMapper = mapper;

        this.menuMapper = menuMapper;
    }

    @Override
    public LinkedList<CategoryVo> getAllCategories() {
        LinkedList<CategoryVo> categoriesVo = null;

        try {
            categoriesVo = categoryMapper.selectAllCategory();
            if (categoriesVo == null || categoriesVo.size() == 0) {
                throw new NotFindException("not find resource");
            }
        } catch (NotFindException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return categoriesVo;
    }

    @Override
    public CategoryVo postCategory(String name) {
        CategoryVo categoryVo = null;

        try {
            categoryVo = categoryMapper.insertCategory(name);
        } catch (MyBatisSystemException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new UnknownException(e.getMessage());
        }

        return categoryVo;
    }

    @Override
    public MenusInCategoryDto deleteCategory(int code) {
        MenusInCategoryDto menusInCategoryDto = null;

        // 카테고리 삭제하고 하위 메뉴도 삭제.
//        try {
//            if (menuMapper.existCategory(code)) {
//                // TODO extension error code
//                throw new InvalidParameterException(String.format("Code(%d) include menus.", code));
//            }
//        } catch (MyBatisSystemException e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            throw new UnknownException(e.getMessage());
//        }

        try {
            LinkedList<MenuVo> menus = menuMapper.deleteByCategory(code);
            if (menus == null) {
                throw new UnknownException(
                    String.format("fail delete menus by category(%d)", code));
            }

            menusInCategoryDto.setMenus(menus);

            CategoryVo categoryVo = categoryMapper.selectByCode(code);
            if (categoryVo == null) {
                throw new NotFindException(String.format("not find code(%d) in category.", code));
            }

            Integer result = categoryMapper.deleteCategory(code);
            if (result == null || result == 0) {
                throw new UnknownException(String.format("fail delete code(%d)", code));
            }

            menusInCategoryDto.setCode(categoryVo.getCode());
            menusInCategoryDto.setName(categoryVo.getName());
            menusInCategoryDto.setOrder(categoryVo.getOrder());
        } catch (NotFindException | UnknownException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new UnknownException(e.getMessage());
        }

        return menusInCategoryDto;
    }
}

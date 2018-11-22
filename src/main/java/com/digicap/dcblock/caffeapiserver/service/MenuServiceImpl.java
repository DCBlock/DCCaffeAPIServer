package com.digicap.dcblock.caffeapiserver.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.digicap.dcblock.caffeapiserver.dto.CategoryVo;
import com.digicap.dcblock.caffeapiserver.dto.MenuDto;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.store.CategoryMapper;
import com.digicap.dcblock.caffeapiserver.store.MenuMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * menu business logic class.
 */
@Service
@Primary
@Slf4j
public class MenuServiceImpl implements MenuService {

    private MenuMapper menuMapper;

    private CategoryMapper categoryMapper;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper, CategoryMapper categoryMapper) {
        this.menuMapper = menuMapper;
        this.categoryMapper = categoryMapper;
    }

    public LinkedHashMap<String, LinkedList<MenuDto>> getAllMenus() {
        // category 테이블에서 목록을 조회.
        LinkedList<CategoryVo> categories = null;

        try {
            categories = categoryMapper.selectAllCategory();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnknownException(e.getMessage());
        }

        if (categories == null || categories.size() == 0) {
            log.error("category is null or 0.");
            throw new NotFindException("category is null or 0.");
        }

        // category 테이블의 code를 기준으로 menus를 조회.
        LinkedHashMap<String, LinkedList<MenuDto>> menus = new LinkedHashMap<>();

        try {
            for (CategoryVo category : categories) {
                LinkedList<MenuDto> menusByCode = getMenusByCategoryCode(category.getCode());
                menus.put(category.getName(), menusByCode);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnknownException(e.getMessage());
        }

        return menus;
    }

    public LinkedHashMap<Integer, LinkedList<MenuDto>> getAllMenusUsingCode() {
        // category 테이블에서 목록을 조회.
        LinkedList<CategoryVo> categories = null;

        try {
            categories = categoryMapper.selectAllCategory();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnknownException(e.getMessage());
        }

        if (categories == null || categories.size() == 0) {
            log.error("category is null or 0.");
            throw new NotFindException("category is null or 0.");
        }

        // category 테이블의 code를 기준으로 menus를 조회.
        LinkedHashMap<Integer, LinkedList<MenuDto>> menus = new LinkedHashMap<>();

        try {
            for (CategoryVo category : categories) {
                LinkedList<MenuDto> menusByCode = getMenusByCategoryCode(category.getCode());
                menus.put(category.getCode(), menusByCode);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnknownException(e.getMessage());
        }

        return menus;
    }

    @Override
    public void deleteMenu(int category, int code) {
        try {
            Integer result = menuMapper.deleteCode(code, category);
            if (result == null || result == 0) {
                throw new NotFindException(String.format("not find menu for delete. Category(%d) Code(%d)", code, category));
            }        
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw e;
        }
    }

    private LinkedList<MenuDto> getMenusByCategoryCode(int code) throws Exception {
        LinkedList<MenuDto> menus = null;

        try {
            menus = menuMapper.selectAllMenus(code);
        } catch (Exception e) {
            throw e;
        }

        return menus;
    }
}

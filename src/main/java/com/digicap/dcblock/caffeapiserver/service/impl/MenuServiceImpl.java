package com.digicap.dcblock.caffeapiserver.service.impl;

import com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants;
import com.digicap.dcblock.caffeapiserver.dto.MenuVo;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.service.MenuService;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.mybatis.spring.MyBatisSystemException;
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
public class MenuServiceImpl implements MenuService, CaffeApiServerApplicationConstants {

    private MenuMapper menuMapper;

    private CategoryMapper categoryMapper;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper, CategoryMapper categoryMapper) {
        this.menuMapper = menuMapper;
        this.categoryMapper = categoryMapper;
    }

    public LinkedHashMap<String, LinkedList<MenuDto>> getAllMenus() throws MyBatisSystemException {
        // category 테이블에서 목록을 조회.
        LinkedList<CategoryVo> categories = categoryMapper.selectAllCategory();
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
    public void deleteMenu(int category, int code) throws MyBatisSystemException {
        Integer result = menuMapper.deleteCode(code, category);
        if (result == null || result == 0) {
            throw new NotFindException(String.format("not find menu for delete. Category(%d) Code(%d)", code, category));
        }
    }

    @Override
    public MenuVo setMenu(MenuVo menuVo) throws MyBatisSystemException {
        // Check.
        if (!menuMapper.existCategory(menuVo.getCategory())) {
            throw new NotFindException(String.format("not find category(%d)", menuVo.getCategory()));
        }

        MenuVo result = menuMapper.insertMenu(menuVo);
        return result;
    }

    @Override
    public LinkedList<MenuDto> updateAllMenusInCategory(int category, LinkedList<MenuDto> menus) throws MyBatisSystemException {
        // Check.
        int size = menuMapper.selectMenuInCategorySize(category);
        if (size != menus.size()) {
            throw new InvalidParameterException("invalid update count for menus");
        }

        // Add Category to instance in list.
        for (MenuDto m : menus) {
            m.setCategory(category);
        }

        // Update.
        menuMapper.updateAllMenuByCategory(menus);
        return null;
    }

    /**
     *
     * @param code
     * @return
     * @throws Exception
     */
    private LinkedList<MenuDto> getMenusByCategoryCode(int code) throws MyBatisSystemException {
        LinkedList<MenuDto> menus = menuMapper.selectAllMenus(code);

        transTypeAndSize(menus);

        return menus;
    }

    /**
     * opt_size, opt_type value trans.
     * @param menus
     * @return
     */
    private LinkedList<MenuDto> transTypeAndSize(LinkedList<MenuDto> menus) {
        for (MenuDto m : menus) {
            switch (m.getOpt_size()) {
                case "0":
                    m.setOpt_size(OPT_SIZE_REGULAR);
                    break;
                case "1":
                    m.setOpt_size(OPT_SIZE_SMALL);
                    break;
            }

            switch (m.getOpt_type()) {
                case "0":
                    m.setOpt_type(OPT_TYPE_HOT);
                    break;
                case "1":
                    m.setOpt_type(OPT_TYPE_ICED);
                    break;
                case "2":
                    m.setOpt_type(OPT_TYPE_BOTH);
                    break;
            }
        }
        return menus;
    }

}

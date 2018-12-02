package com.digicap.dcblock.caffeapiserver.service.impl;

import com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants;
import com.digicap.dcblock.caffeapiserver.dto.MenuVo;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.service.MenuService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.sun.javafx.scene.control.skin.VirtualFlow.ArrayLinkedList;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import net.bytebuddy.dynamic.scaffold.MethodGraph.Linked;
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
    public MenuDto setMenu(MenuDto menuDto) throws MyBatisSystemException {
        // Check.
        if (!menuMapper.existCategory(menuDto.getCategory())) {
            throw new NotFindException(String.format("not find category(%d)", menuDto.getCategory()));
        }

        MenuVo tempVo = toMenuVo(menuDto);
        MenuVo result = menuMapper.insertMenu(tempVo);
        MenuDto tempDto = toMenuDto(result);
        return tempDto;
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

        LinkedList<MenuVo> menusVo = new LinkedList<>();
        for (MenuDto m : menus) {
            menusVo.add(toMenuVo(m));
        }

        // Update.
        menuMapper.updateAllMenuByCategory(menusVo);
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

    private MenuDto toMenuDto(MenuVo v) {
        MenuDto t = new MenuDto();
        t.setCategory(v.getCategory());
        t.setCode(v.getCode());
        t.setOrder(v.getOrder());
        t.setName_en(v.getName_en());
        t.setName_kr(v.getName_en());
        t.setPrice(v.getPrice());
        t.setDc_digicap(v.getDc_digicap());
        t.setDc_covision(v.getDc_covision());
        // TODO EVENT_NAME
        t.setEvent_name("");

        switch (v.getOpt_type()) {
            case 0:
                t.setOpt_type(OPT_TYPE_HOT);
                break;
            case 1:
                t.setOpt_type(OPT_TYPE_ICED);
                break;
            case 2:
                t.setOpt_type(OPT_TYPE_BOTH);
                break;
        }

        switch (v.getOpt_size()) {
            case 0:
                t.setOpt_size(OPT_SIZE_REGULAR);
                break;
            case 1:
                t.setOpt_size(OPT_SIZE_SMALL);
                break;
        }

        return t;
    }

    private MenuVo toMenuVo(MenuDto t) {
        MenuVo v = new MenuVo();
        v.setCategory(t.getCategory());
        v.setCode(t.getCode());
        v.setOrder(t.getOrder());
        v.setName_en(t.getName_en());
        v.setName_kr(t.getName_en());
        v.setPrice(t.getPrice());
        v.setDc_digicap(t.getDc_digicap());
        v.setDc_covision(t.getDc_covision());
        // TODO EVENT_NAME
        //t.setEvent_name("");

        switch (t.getOpt_type()) {
            case OPT_TYPE_HOT:
                v.setOpt_type(0);
                break;
            case OPT_TYPE_ICED:
                v.setOpt_type(1);
                break;
            case OPT_TYPE_BOTH:
                v.setOpt_type(2);
                break;
        }

        switch (t.getOpt_size()) {
            case OPT_SIZE_REGULAR:
                v.setOpt_size(0);
                break;
            case OPT_SIZE_SMALL:
                v.setOpt_size(1);
                break;
        }

        return v;
    }
}

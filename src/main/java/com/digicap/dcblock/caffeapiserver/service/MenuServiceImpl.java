package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.CategoryVo;
import com.digicap.dcblock.caffeapiserver.dto.MenuVo;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.store.CategoryMapper;
import com.digicap.dcblock.caffeapiserver.store.MenuMapper;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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

    public LinkedHashMap<String, LinkedList<MenuVo>> getAllMenus() {
        // category 테이블에서 목록을 조회.
        LinkedList<CategoryVo> categories = null;

        try {
            categories = categoryMapper.getAllCategory();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnknownException(e.getMessage());
        }

        if (categories == null || categories.size() == 0) {
            log.error("category is null or 0.");
            throw new NotFindException("category is null or 0.");
        }

        // category 테이블의 code를 기준으로 menus를 조회.
        LinkedHashMap<String, LinkedList<MenuVo>> menus = new LinkedHashMap<>();

        try {
            for (CategoryVo category : categories) {
                LinkedList<MenuVo> menusByCode = getMenusByCategoryCode(category.getCode());
                menus.put(category.getName(), menusByCode);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnknownException(e.getMessage());
        }

        return menus;
    }

    private LinkedList<MenuVo> getMenusByCategoryCode(int code) throws Exception {
        LinkedList<MenuVo> menus = null;

        try {
            menus = menuMapper.getAllMenus(code);
        } catch (Exception e) {
            throw e;
        }

        return menus;
    }
}

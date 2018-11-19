package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dao.CategoryDao;
import com.digicap.dcblock.caffeapiserver.dao.MenuDao;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.store.CategoryMapper;
import com.digicap.dcblock.caffeapiserver.store.MenuMapper;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * menu business logic class.
 */
@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    private MenuMapper menuMapper;

    private CategoryMapper categoryMapper;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper, CategoryMapper categoryMapper) {
        this.menuMapper = menuMapper;
        this.categoryMapper = categoryMapper;
    }

    public LinkedHashMap<String, LinkedList<MenuDao>> getAllMenus() {
        // category 테이블에서 목록을 조회.
        LinkedList<CategoryDao> categories = null;

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
        LinkedHashMap<String, LinkedList<MenuDao>> menus = new LinkedHashMap<>();

        try {
            for (CategoryDao category : categories) {
                LinkedList<MenuDao> menusByCode = getMenusByCategoryCode(category.getCode());
                menus.put(category.getName(), menusByCode);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnknownException(e.getMessage());
        }

        return menus;
    }

    private LinkedList<MenuDao> getMenusByCategoryCode(int code) throws Exception {
        LinkedList<MenuDao> menus = null;

        try {
            menus = menuMapper.getAllMenus(code);
        } catch (Exception e) {
            throw e;
        }

        return menus;
    }
}

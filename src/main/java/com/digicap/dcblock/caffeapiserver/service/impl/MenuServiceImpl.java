package com.digicap.dcblock.caffeapiserver.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants;
import com.digicap.dcblock.caffeapiserver.dto.CategoryVo;
import com.digicap.dcblock.caffeapiserver.dto.DiscountVo;
import com.digicap.dcblock.caffeapiserver.dto.MenuDto;
import com.digicap.dcblock.caffeapiserver.dto.MenuVo;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.service.MenuService;
import com.digicap.dcblock.caffeapiserver.store.CategoryMapper;
import com.digicap.dcblock.caffeapiserver.store.DiscountMapper;
import com.digicap.dcblock.caffeapiserver.store.MenuMapper;

/**
 * menu business logic class.
 */
@Service
@Primary
public class MenuServiceImpl implements MenuService, CaffeApiServerApplicationConstants {

    private MenuMapper menuMapper;

    private CategoryMapper categoryMapper;

    private DiscountMapper discountMapper;
    
    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper, CategoryMapper categoryMapper, DiscountMapper discountMapper) {
        this.menuMapper = menuMapper;
        this.categoryMapper = categoryMapper;
        this.discountMapper = discountMapper;
    }

    public LinkedHashMap<String, LinkedList<MenuDto>> getAllMenus()
            throws MyBatisSystemException, NotFindException, UnknownException {
        // category 테이블에서 목록을 조회.
        LinkedList<CategoryVo> categories = categoryMapper.selectAllCategory();
        if (categories == null || categories.size() == 0) {
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
            throw new UnknownException(e.getMessage());
        }

        return menus;
    }

    public LinkedHashMap<Integer, LinkedList<MenuDto>> getAllMenusUsingCode()
            throws MyBatisSystemException, NotFindException, UnknownException {
        // category 테이블에서 목록을 조회.
        LinkedList<CategoryVo> categories = categoryMapper.selectAllCategory();
        if (categories == null || categories.size() == 0) {
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
            throw new UnknownException(e.getMessage());
        }

        return menus;
    }

    @Transactional
    @Override
    public void deleteMenu(int category, int code) throws MyBatisSystemException, NotFindException {
        // delete discount
        int result = discountMapper.deleteDiscount(category, code);
        
        result = menuMapper.deleteCode(code, category);
        if (result == 0) {
            throw new NotFindException(String.format("not find menu for delete. Category(%d) Code(%d)", code, category));
        }
    }

    @Transactional
    @Override
    public MenuVo setMenu(MenuDto menuDto) throws MyBatisSystemException, NotFindException {
        // Check.
        if (!menuMapper.existCategory(menuDto.getCategory())) {
            throw new NotFindException(String.format("not find category(%d)", menuDto.getCategory()));
        }

        try {
            MenuVo tempVo = new MenuVo(menuDto);
            MenuVo result = menuMapper.insertMenu(tempVo);
            if (result == null) {
                throw new Exception("fail.");
            }
            
            List<DiscountVo> discounts = new LinkedList<>();
            
            if (menuDto.getDiscounts() != null) {
                Set<String> keys = menuDto.getDiscounts().keySet();
                for (String k : keys) {
                    int discount = menuDto.getDiscounts().getOrDefault(k, (int) 0);
                    discounts.add(new DiscountVo(result.getIndex(), discount, k, null, null));
                }
            }

            // Insert discounts
            discountMapper.insertDiscount(discounts);

            result.setDiscounts(menuDto.getDiscounts());
            return result;
        } catch (Throwable t) {
            throw new UnknownException(t.getMessage());
        }
    }

    @Transactional
    @Override
    public LinkedList<MenuDto> updateAllMenusInCategory(int category, LinkedList<MenuDto> menus)
            throws MyBatisSystemException, InvalidParameterException {
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
            menusVo.add(new MenuVo(m));
        }

        // Update.
        menuMapper.updateAllMenuByCategory(menusVo);
        
        for (MenuVo m : menusVo) {
            if (m.getDiscounts() != null) {
                Set<String> keys = m.getDiscounts().keySet();
                for (String k : keys) {
                    int discount = m.getDiscounts().getOrDefault(k, (int) 0);
                    // Update discounts
                    discountMapper.updateDiscounts(m.getCategory(), m.getCode(), k, discount);
                }
            } 
        }
        
        return null;
    }

    // ---------------------------------------------------------------------------------------------
    // Private Methods

    /**
     *
     * @param category
     * @return
     * @throws Exception
     */
    private LinkedList<MenuDto> getMenusByCategoryCode(int category) throws MyBatisSystemException {
        LinkedList<MenuVo> menus = menuMapper.selectAllMenus(category);

        LinkedList<MenuDto> results = transTypeAndSize(menus);

        for (MenuDto m : results) {
            List<DiscountVo> discounts = discountMapper.selectDiscount(category, m.getCode());
            if (discounts != null) {
                HashMap<String, Integer> h = new HashMap<>();

                for (DiscountVo d : discounts) {
                    h.put(d.getCompany(), (int)d.getDiscount());
                }
                
                m.setDiscounts(h);
            }
        }

        return results;
    }

    /**
     * opt_size, opt_type value trans.
     * 
     * @param menus
     * @return
     */
    private LinkedList<MenuDto> transTypeAndSize(LinkedList<MenuVo> menus) {
        
        LinkedList<MenuDto> results = new LinkedList<>();
        
        for (MenuVo m : menus) {
            MenuDto menuDto = new MenuDto(m);
            results.add(menuDto);
//            switch (m.getOpt_size()) {
//            case 0:
//                m.setOpt_size(OPT_SIZE_REGULAR);
//                break;
//            case 1:
//                m.setOpt_size(OPT_SIZE_SMALL);
//                break;
//            default:
//                throw new InvalidParameterException(String.format("unknown opt_size(%s)", m.getOpt_size()));
//            }
//
//            switch (m.getOpt_type()) {
//            case "0":
//                m.setOpt_type(OPT_TYPE_HOT);
//                break;
//            case "1":
//                m.setOpt_type(OPT_TYPE_ICED);
//                break;
//            case "2":
//                m.setOpt_type(OPT_TYPE_BOTH);
//                break;
//            default:
//                throw new InvalidParameterException(String.format("unknown opt_type(%s)", m.getOpt_type()));
//            }
        }

        return results;
    }

    

    
}

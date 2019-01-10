package com.digicap.dcblock.caffeapiserver.store;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digicap.dcblock.caffeapiserver.dto.DiscountVo;

/**
 * discount table을 사용하는 query들.
 */
public interface DiscountMapper {

    /**
     * discount를 추가
     * @param discounts
     * @return count 
     */
    int insertDiscount(List<DiscountVo> discounts);
    
    /**
     * category, code 메뉴의 discounts를 삭제
     * @param category menu'cateory
     * @param code menu'code
     * @return deleted count
     */
    int deleteDiscount(@Param("category") int category, @Param("code") int code);

//    @Select("SELECT category, code, name_en, name_kr, price, dc_digicap, dc_covision, opt_type, opt_size, event_name FROM menus WHERE category = #{category} ORDER BY menus.order ASC")
//    LinkedList<MenuDto> selectAllMenus(int category);
//
//    @Select("SELECT EXISTS(SELECT 1 FROM menus WHERE code = #{code} AND category = #{category})")
//    boolean existCode(@Param("code") int code, @Param("category") int category);
//
//    @Delete("DELETE FROM menus WHERE code = #{code} AND category = #{category}")
//    Integer deleteCode(@Param("code") int code, @Param("category") int category);
//
//    @Select("SELECT EXISTS(SELECT 1 FROM menus WHERE category = #{category})")
//    boolean existCategory(@Param("category") int category);
//
//    @Select("DELETE FROM menus WHERE category = #{category} RETURNING *")
//    LinkedList<MenuVo> deleteByCategory(@Param("category") int category);
//
//    MenuVo insertMenu(MenuVo menu);
//
//    @Select("SELECT COUNT(*) FROM menus WHERE category = #{category}")
//    int selectMenuInCategorySize(@Param("category") int category);
//
//    Integer updateAllMenuByCategory(LinkedList<MenuVo> menus);
}

package com.digicap.dcblock.caffeapiserver.store;

import java.util.LinkedList;
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
    
    /**
     * category, code의 discounts를 조회
     * 
     * @param category menu'cateory
     * @param code menu'code
     * @return
     */
    LinkedList<DiscountVo> selectDiscount(@Param("category") int category, @Param("code") int code);

    /**
     * Menu(category, code에 해당하는) 의 discount를 업데이트 
     * 
     * @param category menu'category searching menu'index
     * @param code menu'code searching menu'index
     * @param company
     * @param discount
     * @return
     */
    int updateDiscounts(@Param("category") int category, @Param("code") int code, @Param("company") String compnay, @Param("discount") int discount);
    
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
//    Integer updateAllMenuByCategory(LinkedList<MenuVo> menus);
}

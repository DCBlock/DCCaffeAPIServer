package com.digicap.dcblock.caffeapiserver.store;

import com.digicap.dcblock.caffeapiserver.dto.MenuVo;
import java.util.LinkedList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * menus table을 사용하는 query들.
 */
public interface MenuMapper {

    LinkedList<MenuVo> selectAllMenus(int category);

    @Select("SELECT EXISTS(SELECT 1 FROM menus WHERE code = #{code} AND category = #{category})")
    boolean existCode(@Param("code") int code, @Param("category") int category);

    @Delete("DELETE FROM menus WHERE code = #{code} AND category = #{category}")
    Integer deleteCode(@Param("code") int code, @Param("category") int category);

    @Select("SELECT EXISTS(SELECT 1 FROM menus WHERE category = #{category})")
    boolean existCategory(@Param("category") int category);

    @Select("DELETE FROM menus WHERE category = #{category} RETURNING *")
    LinkedList<MenuVo> deleteByCategory(@Param("category") int category);

    MenuVo insertMenu(MenuVo menu);

    @Select("SELECT COUNT(*) FROM menus WHERE category = #{category}")
    int selectMenuInCategorySize(@Param("category") int category);

    Integer updateAllMenuByCategory(LinkedList<MenuVo> menus);
}

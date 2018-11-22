package com.digicap.dcblock.caffeapiserver.store;

import com.digicap.dcblock.caffeapiserver.dto.MenuDto;
import java.util.LinkedList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * menus table을 사용하는 query들.
 */
public interface MenuMapper {

    @Select("SELECT category, code, name_en, name_kr, price, dc_digicap, dc_covision, opt_type, opt_size FROM menus WHERE category = #{category} ORDER BY menus.order ASC")
    LinkedList<MenuDto> selectAllMenus(int category);

    @Select("SELECT EXISTS(SELECT 1 FROM menus WHERE code = #{code} AND category = #{category})")
    boolean existCode(@Param("code") int code, @Param("category") int category);

    @Delete("DELETE FROM menus WHERE code = #{code} AND category = #{category}")
    Integer deleteCode(@Param("code") int code, @Param("category") int category);

    MenuDto insertMenu(@Param("code") int code, @Param("category") int category);
}

package com.digicap.dcblock.caffeapiserver.store;

import com.digicap.dcblock.caffeapiserver.dao.MenuDao;
import java.util.LinkedList;
import org.apache.ibatis.annotations.Select;

/**
 * menus table을 사용하는 query들.
 */
public interface MenuMapper {

    @Select("SELECT code, name_eng, name_kor, price, dc_digicap, dc_covision, opt_type, opt_size FROM menus WHERE category = #{category} ORDER BY no ASC")
    LinkedList<MenuDao> getAllMenus(int category);
}

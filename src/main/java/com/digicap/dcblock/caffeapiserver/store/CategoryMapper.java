package com.digicap.dcblock.caffeapiserver.store;

import com.digicap.dcblock.caffeapiserver.dao.CategoryDao;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import org.apache.ibatis.annotations.Select;

/**
 * category table을 사용하는 query들.
 */
public interface CategoryMapper {

    @Select("SELECT * FROM category")
    public LinkedList<CategoryDao> getAllCategory();
}

package com.digicap.dcblock.caffeapiserver.store;

import com.digicap.dcblock.caffeapiserver.dto.CategoryVo;
import java.util.LinkedList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * category table을 사용하는 query들.
 */
public interface CategoryMapper {

    @Select("SELECT * FROM category ORDER BY category.order ASC")
    LinkedList<CategoryVo> selectAllCategory();

    CategoryVo insertCategory(@Param("name") String name);
}

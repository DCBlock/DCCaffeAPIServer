package com.digicap.dcblock.caffeapiserver.store;

import com.digicap.dcblock.caffeapiserver.dto.CategoryVo;
import java.util.LinkedList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * category table을 사용하는 query들.
 */
public interface CategoryMapper {

  @Select("SELECT * FROM category ORDER BY category.order ASC")
  LinkedList<CategoryVo> selectAllCategory();

  CategoryVo insertCategory(@Param("name") String name);

  @Select("SELECT * FROM category WHERE code = #{code}")
  CategoryVo selectByCode(@Param("code") int code);

  @Delete("DELETE FROM category WHERE code = #{code}")
  Integer deleteCategory(@Param("code") int code);

  Integer updateCategories(LinkedList<CategoryVo> categories);

  @Select("SELECT COUNT(*) FROM category")
  Integer selectAllCategorySize();
}

package com.digicap.dcblock.caffeapiserver.category.mapper;

import org.apache.ibatis.annotations.Select;

public interface CategoryMapper {

    @Select("SELECT * FROM public.\"Category\"")
    public String getTime();
}

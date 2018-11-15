package com.digicap.dcblock.caffeapiserver.psession.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PasswordMapper {

    @Select("SELECT 1 FROM password_session WHERE key = #{key}::uuid")
    Integer existKey(@Param("key") String key);
}

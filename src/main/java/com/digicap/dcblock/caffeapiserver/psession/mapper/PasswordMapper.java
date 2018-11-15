package com.digicap.dcblock.caffeapiserver.psession.mapper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PasswordMapper {

    @Select("SELECT 1 FROM password_session WHERE key = #{key}::uuid")
    Integer existKey(@Param("key") String key);

    LinkedList<LinkedHashMap<String, String>> selectAllSession();
}

package com.digicap.dcblock.caffeapiserver.store;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * password_session table을 사용하는 query들.
 */
@Mapper
public interface PasswordSessionMapper {

    @Select("SELECT 1 FROM password_session WHERE key = #{key}::uuid")
    Integer existKey(@Param("key") String key);

    LinkedList<LinkedHashMap<String, String>> selectAllSession();
}

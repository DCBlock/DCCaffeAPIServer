package com.digicap.dcblock.caffeapiserver.store;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Deprecated
/**
 * purchases_session table을 사용하는 query들.
 */
@Mapper
public interface PurchaseSessionMapper {

    @Select("SELECT 1 FROM purchase_session WHERE key = #{session}::uuid")
    Integer existKey(@Param("key") String key);

    @Insert("INSERT INTO purchase_session (name, email, session) VALUES (#{name}, #{email}, #{session}:uuid)")
    int insertSession(@Param("name") String name, @Param("email") String email, @Param("session") String session);

    @Delete("DELETE FROM purchase_session WHERE session = #{session}::uuid")
    int deleteSession(@Param("session") String session);
}

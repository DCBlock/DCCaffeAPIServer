package com.digicap.dcblock.caffeapiserver.store;

import com.digicap.dcblock.caffeapiserver.dao.UserDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * users table을 사용하는 query들.
 */
public interface UserMapper {

    @Select("SELECT name, email FROM users WHERE rfid = #{rfid}")
    UserDao existUserByRfid(@Param("rfid") String rfid);
}

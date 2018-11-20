package com.digicap.dcblock.caffeapiserver.store;

import com.digicap.dcblock.caffeapiserver.dto.UserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * users table을 사용하는 query들.
 */
public interface UserMapper {

    @Select("SELECT name, company, index FROM users WHERE rfid = #{rfid}")
    UserVo selectUserByRfid(@Param("rfid") String rfid);
}

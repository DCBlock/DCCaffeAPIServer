package com.digicap.dcblock.caffeapiserver.store;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.digicap.dcblock.caffeapiserver.dto.TemporaryUriDto;
import com.digicap.dcblock.caffeapiserver.dto.TemporaryUriVo;

/**
 * mybatis mapper for temporary_uri table
 */
public interface TemporaryUriMapper {

    @Insert("INSERT INTO temporary_uri (user_record_index, name, random_uri) VALUES (#{userRecordIndex}, #{name}, #{random_uri})")
    @SelectKey(statement="SELECT uuid_generate_v1()", keyProperty="random_uri", resultType=String.class, before=true) 
    Integer insertUri(TemporaryUriDto temporaryUriDto);

    @Select("DELETE FROM temporary_uri WHERE random_uri = #{random_uri} RETURNING random_uri, user_record_index AS userRecordIndex, name")
    TemporaryUriVo deleteAndSelectUri(TemporaryUriDto temporaryUriDto);
}

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

    @Insert("INSERT INTO temporary_uri (user_record_index, name, random_uri, search_date_after, search_date_before) "
        + "VALUES (#{userRecordIndex}, #{name}, #{random_uri}, #{searchDateAfter}, #{searchDateBefore})")
    @SelectKey(statement="SELECT uuid_generate_v1()", keyProperty="random_uri", resultType=String.class, before=true) 
    Integer insertUri(TemporaryUriDto temporaryUriDto);

    @Select("DELETE FROM temporary_uri WHERE random_uri = #{random_uri} "
        + "RETURNING random_uri, user_record_index AS userRecordIndex, name, reg_date AS regDate,"
        + "search_date_after AS searchDateAfter, search_date_before AS searchDateBefore")
    TemporaryUriVo deleteAndSelectUri(TemporaryUriDto temporaryUriDto);
}

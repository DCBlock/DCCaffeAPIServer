package com.digicap.dcblock.caffeapiserver.store;

import com.digicap.dcblock.caffeapiserver.dto.CompanyVo;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedList;

/**
 * company table 관련 query
 */
public interface CompanyMapper {

    @Select("SELECT * FROM company ORDER BY index ASC")
    LinkedList<CompanyVo> selectAllCompany();
}

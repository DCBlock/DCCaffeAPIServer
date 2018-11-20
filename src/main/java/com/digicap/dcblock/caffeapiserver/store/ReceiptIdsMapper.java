package com.digicap.dcblock.caffeapiserver.store;

import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdVo2;
import com.digicap.dcblock.caffeapiserver.dto.UserVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * receipt_ids table을 사용하는 query들.
 */
@Mapper
public interface ReceiptIdsMapper {

    @Select("SELECT 1 FROM receipt_ids WHERE receipt_id = #{receiptId}")
    Integer existReceiptId(int receiptId);

    @Insert("INSERT INTO receipt_ids (name, company, receipt_id) VALUES (#{name}, #{company}, #{receiptId})")
    int insertReceiptId(@Param("name") String name, @Param("company") String company, @Param("receiptId") int receiptId);

    @Select("SELECT name, company, user_record_index FROM receipt_ids WHERE receipt_id = #{receiptId} AND regdate BETWEEN (select TIMESTAMP 'today') AND (select TIMESTAMP 'tomorrow')")
    ReceiptIdVo2 selectByReceiptId(@Param("receiptId") int receiptId);
}

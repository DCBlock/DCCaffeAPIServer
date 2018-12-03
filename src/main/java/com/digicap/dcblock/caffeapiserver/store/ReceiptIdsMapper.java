package com.digicap.dcblock.caffeapiserver.store;

import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdVo;
import java.sql.Timestamp;
import org.apache.ibatis.annotations.Delete;
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

    @Insert("INSERT INTO receipt_ids (name, company, receipt_id, user_record_index) "
        + "VALUES (#{name}, #{company}, #{receiptId}, #{index})")
    int insertReceiptId(@Param("name") String name, @Param("company") String company, @Param("receiptId") int receiptId, @Param("index") long index);

    @Select("SELECT name, company, user_record_index, regdate FROM receipt_ids WHERE receipt_id = #{receiptId} AND regdate BETWEEN (select TIMESTAMP 'today') AND (select TIMESTAMP 'tomorrow')")
    ReceiptIdVo selectByReceiptId(@Param("receiptId") int receiptId);

    @Delete("DELETE FROM receipt_ids WHERE receipt_id = #{receiptId}")
    Integer deleteByReceiptId(@Param("receiptId") int receiptId);

    Integer deleteAll(@Param("regDate") Timestamp regDate);
}

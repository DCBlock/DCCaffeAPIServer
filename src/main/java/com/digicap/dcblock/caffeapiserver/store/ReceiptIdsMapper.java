package com.digicap.dcblock.caffeapiserver.store;

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

    @Insert("INSERT INTO receipt_ids (name, email, receipt_id) VALUES (#{name}, #{email}, #{receiptId})")
    int setReceiptId(@Param("name") String name, @Param("email") String email, @Param("receiptId") int receiptId);
}

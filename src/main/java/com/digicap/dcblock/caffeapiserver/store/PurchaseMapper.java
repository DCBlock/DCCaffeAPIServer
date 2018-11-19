package com.digicap.dcblock.caffeapiserver.store;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * purchase table을 사용하는 query들.
 */
public interface PurchaseMapper {

    @Select("SELECT setval('purchase_receipt_id', 0)")
    int initReceiptId();

    @Select("SELECT nextval('purchase_receipt_id')")
    int getReceiptId();

    @Insert("INSERT INTO purchases (receipt_id, name) VALUES (#{receiptId}, #{userName})")
    int setReceiptId(@Param("receiptId") int receiptId, @Param("userName") String userName);
}

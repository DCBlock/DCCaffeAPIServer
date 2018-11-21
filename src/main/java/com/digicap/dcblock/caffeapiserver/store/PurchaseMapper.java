package com.digicap.dcblock.caffeapiserver.store;

import com.digicap.dcblock.caffeapiserver.dto.PurchaseDto;
import java.util.LinkedList;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * purchase table을 사용하는 query들.
 */
public interface PurchaseMapper {

    @Select("SELECT setval('purchase_receipt_id', 0)")
    int updateReceiptId();

    @Select("SELECT nextval('purchase_receipt_id')")
    int selectReceiptId();

    @Insert("INSERT INTO purchases (receipt_id, name, user_record_index) VALUES (#{receiptId}, #{userName}, #{userRecordIndex)")
    int insertReceiptId(@Param("receiptId") int receiptId, @Param("userName") String userName, @Param("userRecordIndex") long index);

    int insertPurchase(PurchaseDto purchaseDto);

    @Select("SELECT EXISTS(select 1 FROM purchases WHERE receipt_id = #{receiptId})")
    boolean existReceiptId(@Param("receiptId") int receiptId);

    LinkedList<PurchaseDto> updateReceiptStatus(@Param("receiptId") int receiptId, @Param("receiptBeforeStatus") int receiptBeforeStatus, @Param("receiptAfterStatus") int receiptAfterStatus);
}

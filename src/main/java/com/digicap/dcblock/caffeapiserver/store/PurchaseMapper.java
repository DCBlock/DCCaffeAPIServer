package com.digicap.dcblock.caffeapiserver.store;

import com.digicap.dcblock.caffeapiserver.dto.PurchaseDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseNewDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseOldDto;

import java.sql.Timestamp;
import java.util.LinkedList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * purchase table을 사용하는 query들.
 */
public interface PurchaseMapper {

    @Select("SELECT setval('purchase_receipt_id', '0')")
    int updateReceiptId();

    @Select("SELECT nextval('purchase_receipt_id')")
    int selectReceiptId();

    @Insert("INSERT INTO purchases (receipt_id, name, user_record_index) VALUES (#{receiptId}, #{userName}, #{userRecordIndex)")
    int insertReceiptId(@Param("receiptId") int receiptId, @Param("userName") String userName,
                        @Param("userRecordIndex") long index);

    int insertPurchase(PurchaseDto purchaseDto);

    boolean existReceiptId(@Param("receiptId") int receiptId, @Param("from") Timestamp from,
                           @Param("to") Timestamp to);

    LinkedList<Timestamp> selectByReceiptId(@Param("receiptId") int receiptId,
        @Param("userRecordIndex") long userRecordIndex);

    LinkedList<PurchaseDto> updateReceiptCancelStatus(@Param("receiptId") int receiptId);

    LinkedList<PurchaseDto> updateReceiptCancelApprovalStatus(@Param("receiptId") int receiptId,
                                                              @Param("from") Timestamp from, @Param("to") Timestamp to);

    LinkedList<PurchaseOldDto> selectAllByUser(@Param("from") Timestamp from, @Param("to") Timestamp to,
                                               @Param("userRecordIndex") long userRecordIndex, @Param("receiptStatus") int receiptStatus);

    LinkedList<PurchaseNewDto> selectAllCancel(@Param("from") Timestamp from,
                                               @Param("to") Timestamp to);

    LinkedList<PurchaseNewDto> selectAllUser(@Param("before") Timestamp before, @Param("after") Timestamp after,
                                             @Param("userRecordIndex") long index);
}

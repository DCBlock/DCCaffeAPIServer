package com.digicap.dcblock.caffeapiserver.store;

import com.digicap.dcblock.caffeapiserver.dto.PurchaseDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseNewDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseVo;
import java.sql.Date;
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
    int insertReceiptId(@Param("receiptId") int receiptId, @Param("userName") String userName, @Param("userRecordIndex") long index);

    int insertPurchase(PurchaseDto purchaseDto);

    @Select("SELECT EXISTS(select 1 FROM purchases WHERE receipt_id = #{receiptId} AND purchase_date = #{purchaseDate})")
    boolean existReceiptId(@Param("receiptId") int receiptId, @Param("purchaseDate") Date date);

    LinkedList<Timestamp> selectByReceiptId(@Param("receiptId") int receiptId);

    LinkedList<PurchaseDto> updateReceiptCancelStatus(@Param("receiptId") int receiptId);

    LinkedList<PurchaseDto> updateReceiptCancelApprovalStatus(@Param("receiptId") int receiptId,
                                                              @Param("purchaseDate") Date date);

    LinkedList<PurchaseVo> selectAllByUser(@Param("_from") Date from, @Param("_to") Date to, @Param("userRecordIndex") long userRecordIndex, @Param("receiptStatus") int receiptStatus);

    LinkedList<PurchaseNewDto> selectAllCancel(@Param("from") Date from,
                                               @Param("to") Date to);
}

package com.digicap.dcblock.caffeapiserver.store;

import com.digicap.dcblock.caffeapiserver.dto.*;

import java.sql.Timestamp;
import java.util.HashMap;
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

    LinkedList<PurchaseNewDto> selectAllCancel(PurchaseWhere w);

    LinkedList<PurchaseNewDto> selectAllUser(@Param("before") Timestamp before, @Param("after") Timestamp after,
                                             @Param("userRecordIndex") long index,
                                             @Param("company") String company);

    LinkedList<PurchaseVo> selectSearchBy(PurchaseWhere w);

    int selectCount(PurchaseWhere w);

    int selectAllCancelCount(PurchaseWhere w);

    HashMap<String, Long> selectBalanceAccounts(PurchaseWhere w);

    /**
     * 지난 달 구매목록에서 사용자, 정산 금액을 조회
     * @return
     */
    LinkedList<HashMap<String, Object>> selectBalanceAccountLastMonth();

    /**
     * 전전달 구매목록 중에 전달에 취소된 금액 조회
     * @return
     */
    LinkedList<HashMap<String, Object>> selectBalanceAccountMonthBeforeLast();

    /**
     * 이월금액을 DB에 저장한다. insert and update.
     * @param params HashMap "record_index", "balance", "email", "name" of Key
     * @return
     */
    int insertCarriedBalanceForward(HashMap<String, Object> params);
}

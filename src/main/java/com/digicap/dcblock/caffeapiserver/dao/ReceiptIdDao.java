package com.digicap.dcblock.caffeapiserver.dao;

import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdDto;
import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdVo;
import java.sql.Timestamp;

/**
 * receipt_ids table Dao interface
 */
public interface ReceiptIdDao {

    int insertByReceipt(ReceiptIdVo vo);

    ReceiptIdDto selectByReceipt(int receiptId);

    int deleteByReceiptId(int receiptId);

    int deleteByRegdate(Timestamp regDate);
}

package com.digicap.dcblock.caffeapiserver.dao;

import java.sql.Timestamp;

import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdDto;
import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdVo;

/**
 * receipt_ids table Dao interface
 */
public interface ReceiptIdDao {

    int insertByReceipt(ReceiptIdDto Dto);

    ReceiptIdVo selectByReceipt(int receiptId);

    int deleteByReceiptId(int receiptId);

    int deleteByRegdate(Timestamp regDate);
}

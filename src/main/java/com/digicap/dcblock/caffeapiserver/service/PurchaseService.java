package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.PurchaseBalanceDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseDto;
import com.digicap.dcblock.caffeapiserver.dto.PurchaseVo;
import com.digicap.dcblock.caffeapiserver.dto.PurchasedDto;
import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdDto;
import com.digicap.dcblock.caffeapiserver.exception.ExpiredTimeException;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.mybatis.spring.MyBatisSystemException;

public interface PurchaseService {

    ReceiptIdDto getReceiptId(String rfid) throws UnknownException, MyBatisSystemException, NotFindException;

    PurchasedDto requestPurchases(int receiptId, List<LinkedHashMap<String, Object>> purchases) 
            throws MyBatisSystemException, NotFindException, InvalidParameterException, UnknownException;

    List<PurchaseDto> cancelPurchases(int receiptId) throws MyBatisSystemException, NotFindException, ExpiredTimeException;

    List<PurchaseDto> cancelApprovalPurchases(int receiptId) throws MyBatisSystemException, NotFindException;

    LinkedList<PurchaseVo> getPurchases(PurchaseDto purchaseDto, Date from, Date to) throws MyBatisSystemException;

    PurchaseBalanceDto getBalanceByRfid(String rfid, Date fromDate, Date toDate) throws MyBatisSystemException, NotFindException;
}

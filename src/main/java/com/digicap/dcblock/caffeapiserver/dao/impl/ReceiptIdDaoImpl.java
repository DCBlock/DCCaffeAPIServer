package com.digicap.dcblock.caffeapiserver.dao.impl;

import java.sql.Timestamp;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.digicap.dcblock.caffeapiserver.dao.ReceiptIdDao;
import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdDto;
import com.digicap.dcblock.caffeapiserver.dto.ReceiptIdVo;

/**
 * receipt_ids Table DAO Implement Class.
 *
 * @author DigiCAP
 */
@Component
@Primary
public class ReceiptIdDaoImpl implements ReceiptIdDao {

  private SqlSessionTemplate sqlSession;

  @Autowired
  public ReceiptIdDaoImpl(SqlSessionTemplate sqlSession) {
    this.sqlSession = sqlSession;
  }

  @Override
  public int insertByReceipt(ReceiptIdVo vo) {//String name, String company, int receiptId, long index) {
    return sqlSession.insert("insertByReceipt", vo);
  }

  @Override
  public ReceiptIdDto selectByReceipt(int receiptId) {
    return sqlSession.selectOne("selectByReceipt", receiptId);
  }

  @Override
  public int deleteByReceiptId(int receiptId) {
    return sqlSession.delete("deleteByReceiptId", receiptId);
  }

  @Override
  public int deleteByRegdate(Timestamp regDate) {
    return sqlSession.delete("deleteByRegdate", regDate);
  }
}

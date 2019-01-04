package com.digicap.dcblock.caffeapiserver.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.digicap.dcblock.caffeapiserver.dao.TemporaryUriDao;
import com.digicap.dcblock.caffeapiserver.dto.TemporaryUriDto;
import com.digicap.dcblock.caffeapiserver.dto.TemporaryUriVo;

/**
 * temporary_uri table DAO Implement Class. 
 *
 * @author DigiCAP
 */
@Component
@Primary
public class TemporaryUriDaoImpl implements TemporaryUriDao {

  private SqlSessionTemplate sqlSession;

  @Autowired
  public TemporaryUriDaoImpl(SqlSessionTemplate sqlSession) {
    this.sqlSession = sqlSession;
  }

  @Override
  public int insert(TemporaryUriVo temporaryUriVo) {
    return sqlSession.insert("insert", temporaryUriVo);
  }

  @Override
  public TemporaryUriDto selectAndDelete(TemporaryUriVo temporaryUriVo) {
    return sqlSession.selectOne("selectAndDelete", temporaryUriVo);
  }
}

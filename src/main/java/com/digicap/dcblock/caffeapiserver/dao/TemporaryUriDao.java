package com.digicap.dcblock.caffeapiserver.dao;

import com.digicap.dcblock.caffeapiserver.dto.TemporaryUriDto;
import com.digicap.dcblock.caffeapiserver.dto.TemporaryUriVo;

/**
 * temporary_uri table DAO interface.
 *
 * @author DigiCAP
 */
public interface TemporaryUriDao {

  int insert(TemporaryUriVo temporaryUriVo);

  TemporaryUriDto selectAndDelete(TemporaryUriVo temporaryUriVo);
}

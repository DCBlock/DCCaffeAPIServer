package com.digicap.dcblock.caffeapiserver.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.digicap.dcblock.caffeapiserver.dao.TemporaryUriDao;
import com.digicap.dcblock.caffeapiserver.dto.TemporaryUriDto;
import com.digicap.dcblock.caffeapiserver.dto.TemporaryUriVo;
import com.digicap.dcblock.caffeapiserver.dto.UserVo;
import com.digicap.dcblock.caffeapiserver.exception.ExpiredTimeException;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.service.TemporaryUriService;
import com.digicap.dcblock.caffeapiserver.store.UserMapper;
import com.digicap.dcblock.caffeapiserver.util.TimeFormat;

/**
 * 임시 URI 관련 기능 Class.
 * 
 * @author DigiCAP
 */
@Service
@Primary
public class TemporaryUriServiceImpl implements TemporaryUriService {

    private TemporaryUriDao temporaryUriDao;
    
    private UserMapper userMapper;

    @Value("${random-uri-expired-minute}")
    private int randomUriExpired;
    
    @Autowired
    public TemporaryUriServiceImpl(TemporaryUriDao temporaryUriDao,UserMapper userMapper) {
        this.userMapper = userMapper;

        this.temporaryUriDao = temporaryUriDao;
    }

    @Override
    public String createTemporaryUri(String rfid, Timestamp after, Timestamp before)
            throws MyBatisSystemException, NotFindException, UnknownException {
        UserVo userVo = Optional.ofNullable(userMapper.selectUserByRfid(rfid))
            .orElseThrow(() -> new NotFindException("not find rfid' user"));

//        TemporaryUriDto temporaryUriDto = new TemporaryUriDto();
//        temporaryUriDto.setName(userVo.getName());
//        temporaryUriDto.setUserRecordIndex(userVo.getIndex());
//        temporaryUriDto.setSearchDateAfter(after);
//        temporaryUriDto.setSearchDateBefore(before);
        
        // Instance
        TemporaryUriVo vo = new TemporaryUriVo(); 
        vo.setUserRecordIndex(userVo.getIndex());
        vo.setName(userVo.getName());
        vo.setSearchDateAfter(after);
        vo.setSearchDateBefore(before);

        // Insert
        if(temporaryUriDao.insert(vo) == 0) {
            throw new UnknownException("DB Error. insert Random URI.");
        }
        
        return vo.getRandomUri();
    }

    @Override
    public TemporaryUriDto existTemporary(String uuid) throws ExpiredTimeException {
//        TemporaryUriDto temporaryUriDto = new TemporaryUriDto();
//        temporaryUriDto.setRandom_uri(uuid);
//        TemporaryUriVo temporaryUriVo = Optional
//            .ofNullable(temporaryUriMapper.deleteAndSelectUri(temporaryUriDto))
//            .orElseThrow(() -> new ExpiredTimeException("expired random uri"));


        TemporaryUriVo vo = new TemporaryUriVo();
        vo.setRandomUri(uuid);

        // Execute Query.
        TemporaryUriDto uriDto = Optional.ofNullable(temporaryUriDao.selectAndDelete(vo))
                .orElseThrow(() -> new ExpiredTimeException("expired random uri"));
                
        // Compare expired Date.
        Date expired = new TimeFormat().getAddMinute(uriDto.getRegDate().getTime(), randomUriExpired);
        if (expired.before(new Date(System.currentTimeMillis()))) {
            throw new ExpiredTimeException("expired random uri");
        }

        return uriDto;
    }
}

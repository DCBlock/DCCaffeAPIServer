package com.digicap.dcblock.caffeapiserver.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import com.digicap.dcblock.caffeapiserver.dto.UserDto;
import com.digicap.dcblock.caffeapiserver.proxy.AdminServer;
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
    
    @Value("${random-uri-expired-minute}")
    private int randomUriExpired;

    @Value("${admin-server}")
    private String adminServer;

    @Value("${api-version}")
    private String apiVersion;

    @Autowired
    public TemporaryUriServiceImpl(TemporaryUriDao temporaryUriDao) {
        this.temporaryUriDao = temporaryUriDao;
    }

    @Override
    public String createTemporaryUri(String rfid, Timestamp after, Timestamp before)
            throws MyBatisSystemException, NotFindException, UnknownException {
        // Get user from AdminServer.
        UserDto userDto = null;

        try {
            userDto = new AdminServer(adminServer, apiVersion).getUserByRfid(rfid);
            if (userDto == null) {
                throw new UnknownException("not find user");
            }
        } catch (Exception e) {
            throw new UnknownException(e.getMessage());
        }

        // Instance
        TemporaryUriVo vo = new TemporaryUriVo(); 
        vo.setUserRecordIndex(userDto.getIndex());
        vo.setName(userDto.getName());
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

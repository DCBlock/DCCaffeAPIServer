package com.digicap.dcblock.caffeapiserver.service.impl;

import com.digicap.dcblock.caffeapiserver.dto.TemporaryUriDto;
import com.digicap.dcblock.caffeapiserver.dto.TemporaryUriVo;
import com.digicap.dcblock.caffeapiserver.dto.UserVo;
import com.digicap.dcblock.caffeapiserver.exception.ExpiredTimeException;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.service.TemporaryUriService;
import com.digicap.dcblock.caffeapiserver.store.TemporaryUriMapper;
import com.digicap.dcblock.caffeapiserver.store.UserMapper;

import com.digicap.dcblock.caffeapiserver.util.ApplicationProperties;
import com.digicap.dcblock.caffeapiserver.util.TimeFormat;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@Slf4j
public class TemporaryUriServiceImpl implements TemporaryUriService {

    private TemporaryUriMapper temporaryUriMapper;
    
    private UserMapper userMapper;

    private ApplicationProperties applicationProperties;

    @Autowired
    public TemporaryUriServiceImpl(TemporaryUriMapper temporaryUriMapper, UserMapper userMapper,
        ApplicationProperties applicationProperties) {
        this.temporaryUriMapper = temporaryUriMapper;
        this.userMapper = userMapper;

        this.applicationProperties = applicationProperties;
    }

    @Override
    public String createTemporaryUri(String rfid, Timestamp before, Timestamp after) throws MyBatisSystemException {
        UserVo userVo = Optional.ofNullable(userMapper.selectUserByRfid(rfid))
            .orElseThrow(() -> new NotFindException("not find rfid' user"));

        // Instance
        TemporaryUriDto temporaryUriDto = new TemporaryUriDto();
        temporaryUriDto.setName(userVo.getName());
        temporaryUriDto.setUserRecordIndex(userVo.getIndex());

        temporaryUriDto.setSearchDateAfter(after);

        temporaryUriDto.setSearchDateBefore(before);

        Optional.ofNullable(temporaryUriMapper.insertUri(temporaryUriDto))
                .orElseThrow(() -> new UnknownException("DB Error. insert Random URI."));
        return temporaryUriDto.getRandom_uri();
    }

    @Override
    public TemporaryUriVo existTemporary(String uuid) {
        TemporaryUriDto temporaryUriDto = new TemporaryUriDto();
        temporaryUriDto.setRandom_uri(uuid);

        TemporaryUriVo temporaryUriVo = Optional
            .ofNullable(temporaryUriMapper.deleteAndSelectUri(temporaryUriDto))
            .orElseThrow(() -> new ExpiredTimeException("expired random uri"));

        Date expired = new TimeFormat().getAddMinute(temporaryUriVo.getRegDate().getTime(),
            applicationProperties.getRandom_uri_expired_minute());
        if (expired.before(new Date(System.currentTimeMillis()))) {
            throw new ExpiredTimeException("expired random uri");
        }

        return temporaryUriVo;
    }
}

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

import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@Slf4j
public class TemporaryUriServiceImpl implements TemporaryUriService {

    private TemporaryUriMapper temporaryUriMapper;
    
    private UserMapper userMapper;
    
    @Autowired
    public TemporaryUriServiceImpl(TemporaryUriMapper temporaryUriMapper, UserMapper userMapper) {
        this.temporaryUriMapper = temporaryUriMapper;
        this.userMapper = userMapper;
    }

    @Override
    public String createTemporaryUri(String rfid) {
        UserVo userVo = Optional.ofNullable(userMapper.selectUserByRfid(rfid))
            .orElseThrow(() -> new NotFindException("not find rfid' user"));

        TemporaryUriDto temporaryUriDto = new TemporaryUriDto();
        temporaryUriDto.setName(userVo.getName());
        temporaryUriDto.setUserRecordIndex(userVo.getIndex());

        Optional.ofNullable(temporaryUriMapper.insertUri(temporaryUriDto))
                .orElseThrow(() -> new UnknownException("DB Error. insert Random URI."));
        return temporaryUriDto.getRandom_uri();
    }

    @Override
    public TemporaryUriVo existTemporary(String uuid) {
//        TemporaryUriVo temporaryUriVo = null;

        TemporaryUriDto temporaryUriDto = new TemporaryUriDto();
        temporaryUriDto.setRandom_uri(uuid);

        TemporaryUriVo temporaryUriVo = Optional.ofNullable(temporaryUriMapper.deleteAndSelectUri(temporaryUriDto))
            .orElseThrow(() -> new ExpiredTimeException("expired uuid"));
//        try {
//            temporaryUriVo = temporaryUriMapper.deleteAndSelectUri(temporaryUriDto);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            throw e;
//        }

//        if (temporaryUriVo != null) {
//            log.debug("delete UUID(%s) from temporary_uri", uuid);
//            return true;
//        }

        return temporaryUriVo;
    }
}

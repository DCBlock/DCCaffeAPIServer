package com.digicap.dcblock.caffeapiserver.psession.controller;

import com.digicap.dcblock.caffeapiserver.psession.mapper.PasswordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/psessions")
@Slf4j
public class PasswordSessionController {

    @Autowired
    PasswordMapper mapper;

    @GetMapping("/{key}")
    String getPasswordSession(@PathVariable String key) {
        // key is uuid type?

//        String temp = UUID.fromString(key).toString();

        try {
            Integer exist = mapper.existKey(key);
            if (exist != null) {
                return String.valueOf(exist);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return "false";
    }
}

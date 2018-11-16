package com.digicap.dcblock.caffeapiserver.controller;

import com.digicap.dcblock.caffeapiserver.store.PasswordSessionMapper;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@RestController
@Slf4j
public class PasswordSessionController {

    @Autowired
    PasswordSessionMapper mapper;

//    @PostMapping("/api/psessions")
    LinkedList<LinkedHashMap<String, String>> getAllPasswordSessions() {
        LinkedList<LinkedHashMap<String, String>> sessions = null;

        try {
            sessions = mapper.selectAllSession();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sessions;
    }

//    @GetMapping("/api/psessions/{key}")
    String getPasswordSession(@PathVariable String key) {
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

package com.digicap.dcblock.caffeapiserver.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    HelloMapper mapper;

    @GetMapping("/hello/test")
    String getHello(@RequestHeader Map<String, String> header) {
        return mapper.getTime();
    }

    @GetMapping("/users")
    String getUser() {
        return "Users";
    }

    @GetMapping("/users/{rfid}/purchases")
    String getPurchases() {
        return "Purchases";
    }
}

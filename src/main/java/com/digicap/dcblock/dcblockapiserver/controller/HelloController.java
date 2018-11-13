package com.digicap.dcblock.dcblockapiserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello/test")
    String getHello() {
        return "Hello";
    }
}

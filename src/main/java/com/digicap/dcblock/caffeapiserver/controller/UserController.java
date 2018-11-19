package com.digicap.dcblock.caffeapiserver.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * user table business logic
 */
@RestController
@RequestMapping("/api/caffe/users")
public class UserController {

    @GetMapping
    String findUser(@RequestParam("rfid") String rfid) {
        //find user

        // make session uuid

        // insert session table
        return rfid;
    }
}

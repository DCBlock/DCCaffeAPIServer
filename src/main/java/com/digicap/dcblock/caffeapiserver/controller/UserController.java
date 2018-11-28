package com.digicap.dcblock.caffeapiserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * user table business logic
 */
@RestController
public class UserController {

    @GetMapping("/api/caffe/users")
    String findUser(@RequestParam("rfid") String rfid) {
        //find user

        // make session uuid

        // insert session table
        return rfid;
    }
}

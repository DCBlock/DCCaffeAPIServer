package com.digicap.dcblock.caffeapiserver.controller;

import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/api/sample/error/1")
    String getException2() throws UnknownException {
        throw new UnknownException("sample. unknown exception");
    }

    @GetMapping("/api/sample/error/2")
    String getException1() throws NotFindException {
        throw new NotFindException("sample. not find resource");
    }
}

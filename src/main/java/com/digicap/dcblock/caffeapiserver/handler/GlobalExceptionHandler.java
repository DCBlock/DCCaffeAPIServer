package com.digicap.dcblock.caffeapiserver.handler;

import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnknownException.class)
    @ResponseBody
    public ResponseEntity handleException(UnknownException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFindException.class)
    @ResponseBody
    public ResponseEntity handleException(NotFindException e) {
        return new ResponseEntity(e.getResponseMessage(), HttpStatus.NOT_FOUND);
    }
}

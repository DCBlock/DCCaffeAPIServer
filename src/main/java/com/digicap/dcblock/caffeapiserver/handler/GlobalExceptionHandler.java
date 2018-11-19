package com.digicap.dcblock.caffeapiserver.handler;

import com.digicap.dcblock.caffeapiserver.exception.ForbiddenException;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnknownException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiError handleException(UnknownException e) {
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getReason());
    }

    @ExceptionHandler(NotFindException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleException(NotFindException e) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), e.getReason());
    }

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleException(InvalidParameterException e) {
        return new ApiError(HttpStatus.BAD_REQUEST.value(), e.getReason());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApiError handleException(ForbiddenException e) {
        return new ApiError(HttpStatus.FORBIDDEN.value(), e.getReason());
    }

    /**
     * Error Response Body DTO.
     */
    @AllArgsConstructor
    class ApiError {

        @Getter
        private int code;

        @Getter
        private String reason;
    }
}

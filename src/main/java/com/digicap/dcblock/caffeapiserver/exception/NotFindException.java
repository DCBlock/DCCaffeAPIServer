package com.digicap.dcblock.caffeapiserver.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFindException extends Exception {

    @Getter
    private MultiValueMap<String, String> responseMessage;

    public NotFindException(String message) {
        responseMessage = new LinkedMultiValueMap<>();
        responseMessage.set("message", message);
        responseMessage.set("code", String.valueOf(HttpStatus.NOT_FOUND));
    }
}

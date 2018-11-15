package com.digicap.dcblock.caffeapiserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "unknown error")
@AllArgsConstructor
public class UnknownException extends Exception {
}

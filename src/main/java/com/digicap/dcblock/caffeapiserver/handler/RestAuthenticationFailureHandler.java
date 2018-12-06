package com.digicap.dcblock.caffeapiserver.handler;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.digicap.dcblock.caffeapiserver.dto.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JWT Error Handler Class.
 * 
 * @author DigiCAP
 *
 */
@Component
public class RestAuthenticationFailureHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, 
            AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(403);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Get Error Message.
        String message = Optional.ofNullable(exception.getCause().getMessage())
                .map(Object::toString)
                .orElse(exception.getMessage());

        // Error is HTTP Status Forbidden.
        ApiError error = new ApiError(403, message);
        byte[] body = new ObjectMapper().writeValueAsBytes(error);
        response.getOutputStream().write(body);
    }
}
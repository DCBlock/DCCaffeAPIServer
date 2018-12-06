package com.digicap.dcblock.caffeapiserver.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.digicap.dcblock.caffeapiserver.exception.ForbiddenException;
import com.digicap.dcblock.caffeapiserver.exception.NotSupportedException;
import com.digicap.dcblock.caffeapiserver.util.ApplicationProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller에 Request 전에 처리할 공통 기능을 구현
 * ex. Check API Version, Valid Client IP.
 * 
 * @author DigiCAP
 *
 */
@Component
@Slf4j
public class ControllerFilter implements HandlerInterceptor {

    private static final String ACCEPT = "Accept";

    private ApplicationProperties properties;

    @Autowired
    public ControllerFilter(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String remoteIp = getRemoteIP(request);

        // 허락된 IP목록만 접속 가능하도록 함
        List<String> allow_remotes = properties.getAllow_remotes();
        if (!allow_remotes.contains(remoteIp)) {
            log.error(remoteIp + " is not allow remote ip.");
            throw new ForbiddenException("not allow remote client");
        }

        // API Version Check
        String apiVersion = request.getHeader(ACCEPT);
        if (!apiVersion.equals(properties.getApi_version())) {
            String message = String.format("not support API Version(%s)", apiVersion);
            log.error(message);
            throw new NotSupportedException(message);
        }

        return true;
    }

    // --------------------------------------------------------------------------------------------
    // Private Methods
    
    /**
     * Get Client IP
     * 
     * @param request
     * @return
     */
    private String getRemoteIP(HttpServletRequest request){
        String ip = request.getHeader("X-FORWARDED-FOR");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}

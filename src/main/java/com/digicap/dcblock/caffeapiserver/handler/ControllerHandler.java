package com.digicap.dcblock.caffeapiserver.handler;

import com.digicap.dcblock.caffeapiserver.exception.ForbiddenException;
import com.digicap.dcblock.caffeapiserver.util.ApplicationProperties;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ControllerHandler implements HandlerInterceptor {

    ApplicationProperties properties;

    @Autowired
    public ControllerHandler(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String remoteIp = getRemoteIP(request);

        List<String> allow_remotes = properties.getAllow_remotes();
        if (!allow_remotes.contains(remoteIp)) {
            throw new ForbiddenException("not allow remote client");
        }

        return false;
    }

    private String getRemoteIP(HttpServletRequest request){
        String ip = request.getHeader("X-FORWARDED-FOR");

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}

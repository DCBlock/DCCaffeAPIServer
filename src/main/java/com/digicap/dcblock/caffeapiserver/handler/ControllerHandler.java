package com.digicap.dcblock.caffeapiserver.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants;
import com.digicap.dcblock.caffeapiserver.dto.ApiError;
import com.digicap.dcblock.caffeapiserver.dto.JwtDto;
import com.digicap.dcblock.caffeapiserver.exception.ForbiddenException;
import com.digicap.dcblock.caffeapiserver.exception.JwtException;
import com.digicap.dcblock.caffeapiserver.exception.NotSupportedException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.proxy.AdminServer;
import com.digicap.dcblock.caffeapiserver.util.ApplicationProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

/**
 * Controller에 Request 전에 처리할 공통 기능을 구현
 * ex. Check API Version, Valid Client IP.
 * 
 * @author DigiCAP
 *
 */
@Component
public class ControllerHandler implements HandlerInterceptor, CaffeApiServerApplicationConstants {

    private static final String ACCEPT = "Accept";

    @Value("${api-version}")
    private String apiVersion;
    
    @Value("${admin-server}")
    private String adminServer;
    
    private ApplicationProperties properties;

    @Autowired
    public ControllerHandler(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String remoteIp = getRemoteIP(request);

        // 허락된 IP목록만 접속 가능하도록 함
        List<String> allow_remotes = properties.getAllow_remotes();
        if (!allow_remotes.contains(remoteIp)) {
            throw new ForbiddenException("not allow remote client");
        }

        // API Version Check
        String apiVersion = request.getHeader(ACCEPT);
        if (!apiVersion.equals(apiVersion)) {
            String message = String.format("not support API Version(%s)", apiVersion);
            throw new NotSupportedException(message);
        }

        // Validate JWT
        String url = request.getRequestURI();
        String domain = getAuthoritiesByUri(url);
        if (domain.equals("MENU")) {
            // Remove 'Bearer' Key.
            String jwt = getJwtFromHeader(request);
            
            // Request Token Valid to AdminServer
            try {
                ApiError error = new AdminServer(properties).validToken(jwt);
                if (error.getCode() != 200) {
                    throw new ForbiddenException(error.getReason());
                }
            } catch (Exception e) {
                throw new UnknownException(e.getMessage());
            }

            // Remove Signature.
            String withoutSignature = removeSignatureJwt(jwt);
            
            // Pairing JWT.
            JwtDto jwtDto = parsingJwt(withoutSignature);
            
            // Check Scope
            if (jwtDto.getScope().equals(SCOPE_ADMIN)) {
                return true;
            } else if (jwtDto.getScope().equals(SCOPE_OPERATOR)) {
                if (jwtDto.getCompany().equals(COMPANY_DIGICAP)) {
                    return true;
                }
            }

            // Check Authority
            if (jwtDto.getAuthroties().contains(AUTHORITY_MANAGEMENT)) {
                return true;
            }
            
            // All extra Error.
            throw new ForbiddenException(String.format("access denied. Scope(%s), Authrotiy(%s), "
                    + "Company(%s)", jwtDto.getScope(), jwtDto.getAuthroties().toString(),jwtDto.getCompany()));
        } //  if (domain.equals("MENU")) {
                
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

    /**
     * Get Domain from Path.
     * 
     * @param path
     * @return
     */
    private String getAuthoritiesByUri(String path) {
        final String PATH_MENU = "/api/caffe/menus";
        
        if (path.startsWith(PATH_MENU)) {
            return "MENU";
        }
        return "";
    }
    
    /**
     * Remove JWT Signature
     * 
     * @param jwt
     * @return
     */
    private String removeSignatureJwt(String jwt) {
        int i = jwt.lastIndexOf('.');
        return jwt.substring(0, i + 1); 
    }
    
    /**
     * Get JWT in HTTP Header.
     * 
     * @param request
     * @return
     */
    private String getJwtFromHeader(HttpServletRequest request) {
        String authorization = Optional.ofNullable(request.getHeader("Authorization"))
                .filter(o -> !o.isEmpty())
                .orElseThrow(() -> new ForbiddenException("not find JWT in Request Header"));
        
        if (!authorization.contains("Bearer")) {
            throw new ForbiddenException("not find JWT in Request Header");
        }

        // Remove 'Bearer' Key.
        String jwt = authorization.replace("Bearer ", "");
        return jwt;
    }
    
    /**
     * Pairing JWT.
     * 
     * @param withoutSignatureJwt
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private JwtDto parsingJwt(String withoutSignatureJwt) {
        // Pairing JWT.
        Jwt<Header,Claims> claims = null;
        try {
            claims = Jwts.parser().parseClaimsJwt(withoutSignatureJwt);
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }

        JwtDto jwtDto = new JwtDto();
        // pairing
        jwtDto.setAuthroties((List<String>)claims.getBody().getOrDefault("authorities", new ArrayList<String>()));
        jwtDto.setCompany(claims.getBody().getOrDefault("company", "").toString().toLowerCase());
        jwtDto.setScope(claims.getBody().getOrDefault("scope", "").toString().toLowerCase());
        
        return jwtDto;
    }
}

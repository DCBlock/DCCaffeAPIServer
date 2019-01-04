package com.digicap.dcblock.caffeapiserver.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants;
import com.digicap.dcblock.caffeapiserver.dto.ApiError;
import com.digicap.dcblock.caffeapiserver.dto.JwtVo;
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
@Slf4j
public class ControllerHandler implements HandlerInterceptor, CaffeApiServerApplicationConstants {

  private static final String ACCEPT = "Accept";

  @Value("${api-version}")
  private String apiVersion;

  @Value("${admin-server}")
  private String adminServer;

  @Value("${check-version-api}")
  private boolean enableVersion;

  @Value("${check-allow-ip}")
  private boolean enableAllowIp;

  @Value("${check-jwt}")
  private boolean enableJwt;

  private ApplicationProperties properties;

  @Autowired
  public ControllerHandler(ApplicationProperties properties) {
    this.properties = properties;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // 허락된 IP목록만 접속 가능하도록 함
    if (enableAllowIp) {
      String remoteIp = getRemoteIP(request);

      String uri = request.getRequestURI();

      // BackOffice 허용 IP는 모든 Caffe API 이용 가능.
      List<String> allowBackOffice = properties.getAllow_ip_backoffice();
      if (!allowBackOffice.contains(uri)) {
        String method = request.getMethod();

        // Kiosk용 API만 허가된 IP 확인.
        List<String> allowKiosk = properties.getAllow_ip_kiosk();
        if (!allowKiosk.contains(remoteIp)) {
          throw new ForbiddenException(String.format("not allow remote client(%s)", remoteIp));
        }

        // Kiosk용 API만 허가.
        if (uri.startsWith("/api/caffe/menus") && method.equals("GET")) {
        } else if (uri.startsWith("/api/caffe/purchases/purchase/receipt/id") && method.equals("POST")) {
        } else if (uri.startsWith("/api/caffe/purchases/purchase/receipt") && method.equals("POST")) {
        } else if (uri.startsWith("/api/caffe/purchases/purchase/receipt") && method.equals("PATCH")) {
        } else if (uri.startsWith("/api/caffe/purchases/purchase/rfid") && method.equals("GET")) {
        } else if (uri.startsWith("/api/caffe/purchases/temporary") && method.equals("POST")) {
        } else {
          throw new ForbiddenException(String.format("not allow remote client(%s)", remoteIp));
        }
      }
    }

    // API Version Check
    if (enableVersion) {
      String version = request.getHeader(ACCEPT);
      if (!version.equals(apiVersion)) {
        String message = String.format("not support API Version(%s)", apiVersion);
        // 로그만 남기고 에러 처리 하지 않음. 현 시점에서 version은 중요하지 않음.
        throw new NotSupportedException(message);
      }
    }

    // Get Method
//        String method = Optional.ofNullable(request.getMethod())
//                .orElse("");
//        if (!method.toUpperCase().equals("GET")) {
//            // Get Content-type.
//            String type = Optional.ofNullable(request.getContentType())
//                    .orElse("");
//            if (!type.equals("application/json")) {
//                String message = String.format("not support content type(%s)", type);
//                throw new NotSupportedException(message);
//            }
//        }

    // Validate JWT
    if (enableJwt) {
      String uri = request.getRequestURI();
      String route = getAuthoritiesByUri(uri);
      String method = request.getMethod();
      if (checkRouteJwt(route, method)) {
        // Remove 'Bearer' Key.
        String jwt = getJwtFromHeader(request);

        // Request Token Valid to AdminServer.
        validateJwt(jwt);

        // Remove Signature.
        String withoutSignature = removeSignatureJwt(jwt);

        // Pairing JWT.
        JwtVo jwtVo = parsingJwt(withoutSignature);

        // Check Scope
        if (jwtVo.getScope().equals(SCOPE_ADMIN) && jwtVo.getCompany().equals(COMPANY_DIGICAP)) {
          return true;
        }

//                // Check Authority
//                if (jwtVo.getAuthorities().contains(AUTHORITY_MANAGEMENT)) {
//                    return true;
//                }

        // All extra Error.
        throw new ForbiddenException(String.format("access denied. Scope(%s), Authrotiy(%s), "
                + "Company(%s)", jwtVo.getScope(), jwtVo.getAuthorities().toString(),
            jwtVo.getCompany()));
      } //  if (domain.equals("MENU")) {
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

  /**
   * Get Route from Path.
   *
   * @param path
   * @return
   */
  private String getAuthoritiesByUri(String path) {
    final String PATH_MENU = "/api/caffe/menus";
    final String PATH_CATEGORIES = "/api/caffe/categories";

    if (path.startsWith(PATH_MENU)) {
      return "MENU";
    } else if (path.startsWith(PATH_CATEGORIES)) {
      return "CATEGORIES";
    }

    return "";
  }

  /**
   * Validate JWT to AdminServer
   *
   * @param jwt
   * @return
   */
  private boolean validateJwt(String jwt) {
    try {
      ApiError error = new AdminServer(adminServer, apiVersion).validToken(jwt);
      if (error.getCode() != 200) {
        throw new ForbiddenException(error.getReason());
      }
    } catch (Exception e) {
      throw new UnknownException(String.format("AdminServer: %s", e.getMessage()));
    }

    return true;
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
  private JwtVo parsingJwt(String withoutSignatureJwt) {
    Jwt<Header,Claims> claims = null;
    try {
      claims = Jwts.parser().parseClaimsJwt(withoutSignatureJwt);
    } catch (Exception e) {
      throw new JwtException(e.getMessage());
    }

    // pairing
    JwtVo jwtVo = new JwtVo(
        (List<String>)claims.getBody().getOrDefault("authorities", new ArrayList<String>()),
        claims.getBody().getOrDefault("company", "").toString().toLowerCase(),
        claims.getBody().getOrDefault("scope", "").toString().toUpperCase());
    return jwtVo;
  }

  /**
   * route와 http method에 따라 JWT 필요 대상을 확인. true면 JWT가 필요한 route.
   *
   * @param route
   * @param method
   * @return
   */
  private boolean checkRouteJwt(String route, String method) {
    if (route.toUpperCase().equals("MENU")) {
      switch (method.toUpperCase()) {
        case "POST":
        case "PATCH":
        case "DELETE":
          return true;
      }
    } else if (route.toUpperCase().equals("CATEGORIES")) {
      switch (method.toUpperCase()) {
        case "POST":
        case "PATCH":
        case "DELETE":
          return true;
      }
    }

    return false;
  }
}

package com.digicap.dcblock.caffeapiserver.handler;

/**
 * JWT Error Handler Class.
 * 
 * @author DigiCAP
 *
 */
//@Component
//public class RestAuthenticationFailureHandler implements AuthenticationEntryPoint {
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, 
//            AuthenticationException exception) throws IOException, ServletException {
//        response.setStatus(403);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
//        // Get Error Message.
//        String message = Optional.ofNullable(exception.getMessage())
//                .map(Object::toString)
//                .orElse(exception.getMessage());
//
//        // Error is HTTP Status Forbidden.
//        ApiError error = new ApiError(403, message);
//        byte[] body = new ObjectMapper().writeValueAsBytes(error);
//        response.getOutputStream().write(body);
//    }
//}
package com.digicap.dcblock.caffeapiserver.proxy;

import org.springframework.web.reactive.function.client.WebClient;

import com.digicap.dcblock.caffeapiserver.dto.ApiError;
import com.digicap.dcblock.caffeapiserver.dto.UserDto;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * AdminServer Request 관련 Class.
 *
 * @author DigiCAP
 */
@AllArgsConstructor
public class AdminServer {

    private String server;

    private String apiVersion;

    /**
     * Get User from AdminServer
     *
     * @param rfid
     * @return
     * @throws Exception
     */
    public UserDto getUserByRfid(String rfid) throws Exception {
        final String uri = String.format("%s/users?rfid=%s", server, rfid);

        WebClient webClient = WebClient
                .builder()
                .baseUrl(uri)
                .defaultHeader("Accept", apiVersion)
                .build();

        Mono<UserDto> result = webClient.get()
                .retrieve()
                .bodyToMono(UserDto.class);

        UserDto userByRfid = result.block();
        return userByRfid;
    }

    /**
     * Valid Token from Admin Server.
     *
     * @param token
     * @return
     * @throws Exception
     */
    public ApiError validToken(String token) throws Exception {
        final String uri = String.format("%s/validation", server);
        final String tokens = String.format("Bearer %s", token);

        WebClient webClient = WebClient
                .builder()
                .baseUrl(uri)
                .defaultHeader("Accept", apiVersion)
                .defaultHeader("Authorization", tokens)
                .build();

        Mono<ApiError> result = webClient.post()
                .retrieve()
                .bodyToMono(ApiError.class);

        ApiError apiError = result.block();
        return apiError;
    }
}

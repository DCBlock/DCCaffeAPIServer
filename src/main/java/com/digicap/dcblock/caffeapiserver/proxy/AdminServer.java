package com.digicap.dcblock.caffeapiserver.proxy;

import com.digicap.dcblock.caffeapiserver.dto.ApiError;
import com.digicap.dcblock.caffeapiserver.dto.UserDto;
import com.digicap.dcblock.caffeapiserver.util.ApplicationProperties;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class AdminServer {

    private ApplicationProperties properties;

    public AdminServer(ApplicationProperties properties) {
        this.properties = properties;
    }

    /**
     * Get User from AdminServer
     *
     * @param rfid
     * @return
     * @throws Exception
     */
    public UserDto getUserByRfid(String rfid) throws Exception {
        final String uri = String.format("%s/users?rfid=%s", properties.getAdmin_server(), rfid);
        final String accept = properties.getApi_version();

        WebClient webClient = WebClient
            .builder()
            .baseUrl(uri)
            .defaultHeader("Accept", accept)
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
        final String uri = String.format("%s/validation", properties.getAdmin_server());
        final String accept = properties.getApi_version();
        final String tokens = String.format("Bearer %s", token);

        WebClient webClient = WebClient
            .builder()
            .baseUrl(uri)
            .defaultHeader("Accept", accept)
            .defaultHeader("Authorization", tokens)
            .build();

        Mono<ApiError> result = webClient.post()
            .retrieve()
            .bodyToMono(ApiError.class);

        ApiError apiError = result.block();
        return apiError;
    }
}

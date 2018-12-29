package com.digicap.dcblock.caffeapiserver.dto;

import java.util.List;

import lombok.*;

@Getter
@ToString
@RequiredArgsConstructor
public class JwtVo {

    @NonNull
    private List<String> authorities;

    @NonNull
    private String company;

    @NonNull
    private String scope;
}

package com.digicap.dcblock.caffeapiserver.type;

import lombok.RequiredArgsConstructor;

/**
 * 음료수 사이즈를 정의
 * @author DigiCAP
 *
 */
@RequiredArgsConstructor
public enum OptSize {

    REGULAR(0),
    SMALL(1);
    
    final int code;
}

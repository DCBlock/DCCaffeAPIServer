package com.digicap.dcblock.caffeapiserver.type;

import lombok.RequiredArgsConstructor;

/**
 * 음료수의 HOT, ICED, BOTH를 정의
 * @author DigiCAP
 *
 */
@RequiredArgsConstructor
public enum OptType {
    HOT(0),
    ICED(1),
    BOTH(2);
    
    final int code;
}

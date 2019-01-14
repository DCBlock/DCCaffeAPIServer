package com.digicap.dcblock.caffeapiserver.type;

import lombok.RequiredArgsConstructor;

/**
 * Receipt Status를 정의한다.
 * @author DigiCAP
 *
 */
@RequiredArgsConstructor
public enum ReceiptStatus {

    INIT(-1),       // 초깃값
    PURCHASE(0),    // 구매
    CANCEL(1),      // 구매취소요청
    CANCELED(2);    // 구매취소완료
    
    final int code;
}

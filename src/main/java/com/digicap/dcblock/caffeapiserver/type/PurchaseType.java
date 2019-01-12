package com.digicap.dcblock.caffeapiserver.type;

import java.util.Arrays;

/**
 * 구매종류를 정의
 * 
 * @author DigiCAP
 *
 */
public enum PurchaseType {

    MONTH(0),
    GUEST(1),
    CARRIED(2),
    EMPTY(3);

    final int code;
    
    private PurchaseType(int code) {
        this.code = code;
    }

    public static PurchaseType findByType(int code) {
       return Arrays.stream(PurchaseType.values())
               .filter(type -> code >= 0)
//               .filter(purchaseType -> PurchaseType.hasPurchaseType(code))
               .filter(type -> type.ordinal() == code)
               .findFirst()
               .orElse(EMPTY);
    }
    
    public static boolean hasPurchaseType(int code) {
        return Arrays.stream(PurchaseType.values())
                .anyMatch(v -> v.ordinal() == code);
    }
}

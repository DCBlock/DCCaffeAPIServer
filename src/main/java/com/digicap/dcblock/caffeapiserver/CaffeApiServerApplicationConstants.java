package com.digicap.dcblock.caffeapiserver;

/**
 * Caffe Api Server Constants
 */
public interface CaffeApiServerApplicationConstants {

    // for Request
    String KEY_RFID = "rfid";
    String KEY_PURCHASES = "purchases";
    String KEY_PURCHASE_AFTER = "purchase_after";
    String KEY_PURCHASE_BEFORE = "purchase_before";
    String KEY_PURCHASE_TYPE = "purchase_type";

    // for Response
    String KEY_PURCHASE_CANCELS = "purchase_cancels";
    String KEY_PURCHASE_CANCELEDS = "purchase_canceleds";
    String KEY_URI = "uri";

    int RECEIPT_STATUS_READY = -1;
    int RECEIPT_STATUS_PURCHASE = 0;
    int RECEIPT_STATUS_CANCEL = 1;
    int RECEIPT_STATUS_CANCELED = 2;

    String OPT_SIZE_REGULAR = "REGULAR";
    String OPT_SIZE_SMALL = "SMALL";

    String OPT_TYPE_HOT = "HOT";
    String OPT_TYPE_ICED = "ICED";
    String OPT_TYPE_BOTH = "BOTH";

    int PURCHASE_TYPE_MONTH = 0;
    int PURCHASE_TYPE_GUEST = 1;

    // for JWT ------------------------------------------------------------------------------------
    
    // SCOPE in JWT.
    String SCOPE_ADMIN = "admin";
    String SCOPE_OPERATOR = "operator";
    String SCOPE_USER = "user";
    
    // AUTHRORIES in JWT.
    String AUTHORITY_MANAGEUSER = "ROLE_MANAGEUSER";
    String AUTHORITY_MANAGEMENT = "ROLE_MANAGEMENT";
    String AUTHORITY_VIEWSTATISTICS = "ROLE_VIEWSTATISTICS";
    String AUTHORITY_MANAGEADMIN = "ROLE_MANAGEADMIN";

    // COMPANY in JWT.
    String COMPANY_DIGICAP = "digicap";
    String COMPANY_COVISION = "covision";
}

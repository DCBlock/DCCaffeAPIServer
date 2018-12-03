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
}

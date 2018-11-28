package com.digicap.dcblock.caffeapiserver;

/**
 * Caffe Api Server Constants
 */
public interface CaffeApiServerApplicationConstants {

    // for Request
    String KEY_RFID = "rfid";
    String KEY_PURCHASES = "purchases";

    // for Response
    String KEY_PURCHASE_CANCELS = "purchase_cancels";
    String KEY_PURCHASE_CANCELEDS = "purchase_canceleds";
    String KEY_URI = "uri";

    int RECEIPT_STATUS_READY = 0;
    int RECEIPT_STATUS_PURCHASE = 1;
    int RECEIPT_STATUS_CANCEL = 2;
    int RECEIPT_STATUS_CANCELED = 3;
}

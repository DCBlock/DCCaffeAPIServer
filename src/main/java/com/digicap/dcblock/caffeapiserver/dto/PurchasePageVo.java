package com.digicap.dcblock.caffeapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.LinkedList;

@Getter
@AllArgsConstructor
public class PurchasePageVo {

    private int total_count;

    private LinkedList<Purchase> list;
}

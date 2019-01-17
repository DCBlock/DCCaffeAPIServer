package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Setter
@Getter
@JsonPropertyOrder({"total_pages", "lists"})
public class PurchaseSearchPageDto {

    @JsonProperty("total_pages")
    private int totalPages;

    private LinkedList<PurchaseSearchDto> list;
}

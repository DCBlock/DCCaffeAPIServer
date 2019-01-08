package com.digicap.dcblock.caffeapiserver.dto;

import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.LinkedList;

import static com.digicap.dcblock.caffeapiserver.CaffeApiServerApplicationConstants.*;

@Setter
@Getter
@JsonPropertyOrder({"total_pages", "lists"})
public class PurchaseSearchPageDto {

    @JsonProperty("total_pages")
    private int totalPages;

    private LinkedList<PurchaseSearchDto> list;
}

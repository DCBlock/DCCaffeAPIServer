package com.digicap.dcblock.caffeapiserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;

@Setter
@Getter
@ToString
@JsonPropertyOrder({"total_count", "lists"})
public class SettlementReportPageDto {

    @JsonProperty("total_count")
    private int totalCount;

    private LinkedList<SettlementReportDto> lists;
}

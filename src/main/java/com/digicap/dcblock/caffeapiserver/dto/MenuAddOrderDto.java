package com.digicap.dcblock.caffeapiserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MenuAddOrderDto extends MenuDto {

    private int order;
}

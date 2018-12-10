package com.digicap.dcblock.caffeapiserver.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class JwtDto {
    
    private List<String> authroties;
   
    private String company;
    
    private String scope;
}

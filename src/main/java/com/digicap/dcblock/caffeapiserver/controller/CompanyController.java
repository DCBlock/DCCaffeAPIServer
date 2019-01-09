package com.digicap.dcblock.caffeapiserver.controller;

import com.digicap.dcblock.caffeapiserver.dto.CompanyDto;
import com.digicap.dcblock.caffeapiserver.dto.CompanyVo;
import com.digicap.dcblock.caffeapiserver.store.CompanyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

/**
 * DCaffe 를 사용할 수 있는 회사관련 Controller
 * TODO 미구현 Class
 * @author DigiCAP
 */
@RestController
@Slf4j
public class CompanyController {

    private CompanyMapper companyMapper;

    // -------------------------------------------------------------------------
    // Constructor

    @Autowired
    public CompanyController(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    // -------------------------------------------------------------------------
    // Public Methods
    // Simple하게 Service 없이 직접

//    @GetMapping("/api/caffe/companies")
    LinkedList<CompanyDto> getCompany() throws Exception {
        LinkedList<CompanyVo> companies = companyMapper.selectAllCompany();

        LinkedList<CompanyDto> results = new LinkedList<>();

        if (companies != null) {
            for (CompanyVo c : companies) {
                results.add(new CompanyDto(c));
            }
        }

        return results;
    }
}

package com.digicap.dcblock.caffeapiserver.controller;

import com.digicap.dcblock.caffeapiserver.exception.NotImplementedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 서비스하지 않는 URI Controller.
 *
 * @author DigiCAP
 */
@RestController
public class NotImplementedController {

  @RequestMapping("/**")
  void getError() {
    throw new NotImplementedException();
  }
}

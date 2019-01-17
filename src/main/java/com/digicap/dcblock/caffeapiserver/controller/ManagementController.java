package com.digicap.dcblock.caffeapiserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ManagementController {

    @GetMapping("/managements")
    ModelAndView managements() {
        ModelAndView view = new ModelAndView("management");
        view.addObject("title", "Management");
        return view;
    }
}

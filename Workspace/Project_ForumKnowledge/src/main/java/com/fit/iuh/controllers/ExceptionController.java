package com.fit.iuh.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/403")
public class ExceptionController {
    @RequestMapping("")
    public String accessDenied() {
        return "403";
    }
}

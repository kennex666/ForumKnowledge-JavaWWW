package com.fit.iuh.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Đăng nhập | iDev4rum"); // Truyền tiêu đề trang
        return "login";
    }
}

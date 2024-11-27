package com.fit.iuh.controllers;

import com.fit.iuh.entites.User;
import com.fit.iuh.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Đăng nhập | iDev4rum"); // Truyền tiêu đề trang
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Đăng ký | iDev4rum"); // Truyền tiêu đề trang
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Đăng ký | iDev4rum"); // Truyền tiêu đề trang
            return "register";
        }

        userService.save(user);

        return "redirect:/login?registerSuccess";
    }
}

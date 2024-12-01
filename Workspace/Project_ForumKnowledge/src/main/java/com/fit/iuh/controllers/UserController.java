package com.fit.iuh.controllers;

import com.fit.iuh.entites.User;
import com.fit.iuh.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user-profile")
    public String getUser(Model model, @RequestParam("id") int id) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "views_user/user-profile-main";
    }

    @GetMapping("/user-posts")
    public String getUserPosts(Model model, @RequestParam("id") int id) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "views_user/user-profile-posts";
    }

    @GetMapping("/user-about")
    public String getUserAbout(Model model, @RequestParam("id") int id) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "views_user/user-profile-about";
    }

}

package com.fit.iuh.controllers;

import com.fit.iuh.entites.User;
import com.fit.iuh.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/author")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String getUser(Model model, @PathVariable("id") int id, @RequestParam(value = "tab", required = false) String tab) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("listPost", user.getPosts());
        if (tab != null) {
            if (tab.equals("about")) {
                return "views_user/user-profile-about";
            } else {
                return "views_user/user-profile-main";
            }
        }
        return "views_user/user-profile-main";
    }
}

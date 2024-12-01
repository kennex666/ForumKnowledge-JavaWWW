package com.fit.iuh.controllers;

import com.fit.iuh.entites.User;
import com.fit.iuh.enums.UserAccountState;
import com.fit.iuh.services.UserService;
import com.fit.iuh.utilities.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String index(Model model) {
        List<User> users = userService.getAllUsers();
        UserAccountState[] states = UserAccountState.values();
        model.addAttribute("users", users);
        model.addAttribute("states", states);
        
        String currentEmail = SpringContext.getCurrentUserEmail();
        User currentUser = userService.findUserByEmail(currentEmail);
        model.addAttribute("currentUser", currentUser);
        
        return "views_admin/user-list";
    }

    @GetMapping("/state-change")
    public String changeState(@RequestParam("id") int id, @RequestParam("state") UserAccountState state) {
        userService.changeState(id, state);
        return "redirect:/admin/users/";
    }

}

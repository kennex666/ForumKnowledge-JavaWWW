package com.fit.iuh.controllers;

import com.fit.iuh.entites.User;
import com.fit.iuh.enums.UserAccountState;
import com.fit.iuh.services.UserService;
import com.fit.iuh.utilities.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public String index(
            Model model,
            @RequestParam(name = "skip", defaultValue = "1") int skip,
            @RequestParam(name = "limit", defaultValue = "10") int limit
    ) {
        skip = skip < 1 ? 1 : skip;
        limit = limit < 1 ? 10 : limit;
        int[] limits = new int[]{10, 25, 50, 100};
        int[] roles = new int[]{0, 1, 2};

        Page<User> users = userService.findUsersWithCondition(skip, limit, true);
        UserAccountState[] states = UserAccountState.values();

        model.addAttribute("users", users.getContent());
        model.addAttribute("currentPage", users.getNumber() + 1);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("limit", limit);
        model.addAttribute("baseUrl", "/admin/users");
        model.addAttribute("states", states);
        model.addAttribute("roles", roles);
        model.addAttribute("limits", limits);
        
        String currentEmail = SpringContext.getCurrentUserEmail();
        User currentUser = userService.findUserByEmail(currentEmail);
        model.addAttribute("currentUser", currentUser);
        
        return "views_admin/user-list";
    }

}

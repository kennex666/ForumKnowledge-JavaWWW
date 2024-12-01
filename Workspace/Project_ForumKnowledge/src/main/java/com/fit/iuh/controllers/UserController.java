package com.fit.iuh.controllers;

import com.fit.iuh.entites.User;
import com.fit.iuh.services.PostService;
import com.fit.iuh.services.UserService;
import com.fit.iuh.utilities.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/author")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user) {
        userService.edit(user);
        return "redirect:/author/" + user.getUserId();
    }

    @GetMapping("/{id}")
    public String getUser(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(value = "tab", required = false) String tab, @PathVariable("id") int id) {
        User user = userService.findUserById(id);
        Pageable pageable = PageRequest.of(page, 10);  // 10 bài viết mỗi trang
        User currentUser = userService.findUserByEmail(
                SpringContext.getCurrentUserEmail()
        );

        model.addAttribute("postsPage", postService.findForUser(pageable, user.getUserId()));
        model.addAttribute("user", user);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isFollowing", userService.isFollowing(currentUser.getUserId(), user.getUserId()).size() > 0);
        if (tab != null) {
            if (tab.equals("about")) {
                return "views_user/user-profile-about";
            } else  if (tab.equals("edit")) {
                return "views_user/user-profile-edit";
            }else {
                return "views_user/user-profile-main";
            }
        }

        return "views_user/user-profile-main";
    }
}

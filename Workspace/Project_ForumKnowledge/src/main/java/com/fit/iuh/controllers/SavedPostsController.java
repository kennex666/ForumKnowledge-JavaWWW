package com.fit.iuh.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fit.iuh.entites.BookMark;
import com.fit.iuh.entites.User;
import com.fit.iuh.services.BookMarkService;
import com.fit.iuh.services.UserService;
import com.fit.iuh.utilities.SpringContext;

@Controller
@RequestMapping("/save")
public class SavedPostsController {

    @Autowired
    private BookMarkService bookMarkService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getSavedPosts(Model model) {
        String currentEmail = SpringContext.getCurrentUserEmail();
        User currentUser = userService.findUserByEmail(currentEmail);
        List<BookMark> bookmarks = bookMarkService.findAllByUserId(currentUser.getUserId());
        model.addAttribute("bookmarks", bookmarks);
        return "views_user/saved-posts"; 
    }
    
    @PostMapping("/delete")
    public String deleteBookmark(@RequestParam("postId") int postId) {
        String currentEmail = SpringContext.getCurrentUserEmail();
        User currentUser = userService.findUserByEmail(currentEmail);
        bookMarkService.deleteByUserIdAndPostId(currentUser.getUserId(), postId);
        return "redirect:/save";
    }
}

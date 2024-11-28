package com.fit.iuh.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fit.iuh.entites.Post;
import com.fit.iuh.services.PostService;



@Controller
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private PostService postService;

    @GetMapping
    public String getAllPosts(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts); 
        return "blog"; 
    }
}
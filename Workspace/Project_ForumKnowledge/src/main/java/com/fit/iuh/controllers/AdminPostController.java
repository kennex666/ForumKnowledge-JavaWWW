package com.fit.iuh.controllers;

import com.fit.iuh.entites.Post;
import com.fit.iuh.enums.PostState;
import com.fit.iuh.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/posts")
public class AdminPostController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String index(Model model) {
        List<Post> posts = postService.findAll();
        PostState[] states = PostState.values();
        model.addAttribute("posts", posts);
        model.addAttribute("states", states);
        return "admin/post-list";
    }

    @GetMapping("/state-change")
    public String stateChange(@RequestParam("id") int id, @RequestParam("state") PostState state) {
        postService.changeState(id, state);
        return "redirect:/admin/posts/";
    }

}

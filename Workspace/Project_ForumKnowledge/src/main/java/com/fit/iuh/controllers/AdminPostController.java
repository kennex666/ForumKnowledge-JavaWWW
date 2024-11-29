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

    @GetMapping("")
    public String index(
            Model model,
            @RequestParam(name = "skip", defaultValue = "1") int skip,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "isDesc", defaultValue = "true") boolean isDesc
    ) {
        skip = skip < 1 ? 1 : skip;
        limit = limit < 1 ? 10 : limit;

        List<Post> posts = postService.findPostsWithCondition(skip, limit, isDesc);
        PostState[] states = PostState.values();
        model.addAttribute("posts", posts);
        model.addAttribute("states", states);
        return "views_admin/post-list";
    }

}

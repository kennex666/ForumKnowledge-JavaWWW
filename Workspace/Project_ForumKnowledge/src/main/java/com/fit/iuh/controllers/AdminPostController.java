package com.fit.iuh.controllers;

import com.fit.iuh.entites.Post;
import com.fit.iuh.enums.PostState;
import com.fit.iuh.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
            @RequestParam(name = "limit", defaultValue = "10") int limit
    ) {
        skip = skip < 1 ? 1 : skip;
        limit = limit < 1 ? 10 : limit;
        int[] limits = new int[]{10, 25, 50, 100};

        Page<Post> posts = postService.findPostsWithCondition(skip, limit, true);
        PostState[] states = PostState.values();

        model.addAttribute("posts", posts.getContent());
        model.addAttribute("currentPage", posts.getNumber() + 1);
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("limit", limit);
        model.addAttribute("baseUrl", "/admin/posts");
        model.addAttribute("states", states);
        model.addAttribute("limits", limits);
        return "views_admin/post-list";
    }

}

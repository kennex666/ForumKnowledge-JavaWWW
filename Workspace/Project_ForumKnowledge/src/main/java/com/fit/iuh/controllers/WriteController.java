package com.fit.iuh.controllers;

import com.fit.iuh.services.PostService;
import com.fit.iuh.services.TopicService;
import com.fit.iuh.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/write")
public class WriteController {

    @Autowired
    private PostService postService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String write(Model model) {
        model.addAttribute("title", "Viết bài | iDev4rum");
        return "views_user/write-post";
    }
}

package com.fit.iuh.controllers;

import com.fit.iuh.entites.Topic;
import com.fit.iuh.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // Service
    @Autowired
    private TopicService topicService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Topic
    @GetMapping("/topic")
    public String topic(Model model) {
        // Get all topics
        List<Topic> topics = topicService.getAll();
        model.addAttribute("topics", topics);
        return "topic";
    }

    // Layouts pages
    @GetMapping("/table")
    public String table() { return "table"; }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @GetMapping("/404")
    public String error404() {
        return "404";
    }

    @GetMapping("/blank")
    public String blank() {
        return "blank";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}
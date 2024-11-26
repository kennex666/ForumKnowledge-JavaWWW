package com.fit.iuh.controllers;

import com.fit.iuh.entites.Topic;
import com.fit.iuh.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public String topic(Model model, @RequestParam(defaultValue = "0") int numberPage, @RequestParam(defaultValue = "10") int size) {
        // Get all topics
        Page<Topic> page = topicService.getPage(numberPage, size);
        model.addAttribute("topics", page.getContent());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", numberPage);
        // thứ tự cua du lieu dau tien cua trang
        model.addAttribute("start", numberPage * size + 1);
        // thứ tự cua du lieu cuoi cung cua trang
        model.addAttribute("end", numberPage * size + page.getNumberOfElements());

        model.addAttribute("quantityTopic", page.getTotalElements());
        return "topic";
    }

    @GetMapping("/topic/addform")
    public String addTopicForm(Model model) {
        model.addAttribute("topic", new Topic());
        return "add-topic";
    }

    @PostMapping("/topic/add")
    public String addTopic(Topic topic) {
        topic.setCreatedAt(new Date(System.currentTimeMillis()));
        topic.setUpdatedAt(new Date(System.currentTimeMillis()));
        topicService.add(topic);
        return "redirect:/admin/topic";
    }

    @PostMapping("/topic/edit")
    @ResponseBody
    public Map<String, Object> editTopic(@RequestParam int id, @RequestParam String name, @RequestParam String hashtag) {
        Map<String, Object> response = new HashMap<>();
        try {
            Topic topic = topicService.getById(id);
            topic.setName(name.trim());
            topic.setHashtag(hashtag.trim());
            topic.setUpdatedAt(new Date(System.currentTimeMillis()));

            topicService.update(topic);

            response.put("status", "success");
            response.put("message", "Cập nhật thành công!");

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Lỗi: " + e.getMessage());
        }
        return response;
    }


    @GetMapping("/topic/delete")
    public String deleteTopic(@RequestParam int id) {
        topicService.delete(id);
        return "redirect:/admin/topic";
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
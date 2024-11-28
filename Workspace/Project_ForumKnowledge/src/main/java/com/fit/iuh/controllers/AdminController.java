package com.fit.iuh.controllers;

import com.fit.iuh.entites.Post;
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
        return "views_admin/index";
    }
    /*
      ** Topic
      * 1. Hiển thị danh sách topic
      * 2. Thêm topic
      *     2.1 Hiển thị form thêm topic,
      *     2.2 Thêm topic
      * 3. Xem chi tiết topic
      * 4. Sửa topic
      * 5. Xóa topic
     */

    // 1. Hiển thị danh sách topic
    @GetMapping("/topic")
    public String topic(Model model,
                        @RequestParam(defaultValue = "0") int numberPage,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(required = false) String key) {

        Page<Topic> page;
        if (key != null && !key.trim().isEmpty()) {
            // Nếu có từ khóa tìm kiếm
            page = topicService.searchByKeywordWithPaging(key, numberPage, size);
            model.addAttribute("key", key);
        } else {
            page = topicService.getPage(numberPage, size);
        }

        // Các attribute khác giữ nguyên
        model.addAttribute("topics", page.getContent());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", numberPage);
        model.addAttribute("start", numberPage * size + 1);
        model.addAttribute("end", numberPage * size + page.getNumberOfElements());
        model.addAttribute("quantityTopic", page.getTotalElements());
        model.addAttribute("size", size);

        return "views_admin/topic";
    }
    // 2. Thêm topic
    @GetMapping("/topic/addform")
    public String addTopicForm(Model model) {
        model.addAttribute("topic", new Topic());
        return "views_admin/add-topic";
    }

    // 2.1 Hiển thị form thêm topic
    @PostMapping("/topic/add")
    public String addTopic(Topic topic) {
        topic.setCreatedAt(new Date(System.currentTimeMillis()));
        topic.setUpdatedAt(new Date(System.currentTimeMillis()));
        topicService.add(topic);
        return "redirect:/admin/topic";
    }

    // 3. Xem chi tiết topic
    @GetMapping("/topic/detail/{id}")
    public String viewTopic(@PathVariable int id, Model model) {

        Topic topic = topicService.getById(id);
        model.addAttribute("topic", topic);

        List<Post> posts = topic.getPosts();
        model.addAttribute("posts", posts);

        return "views_admin/view-topic";
    }

    // 4. Sửa topic
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

    // 5. Xóa topic
    @GetMapping("/topic/delete")
    public String deleteTopic(@RequestParam int id) {
        topicService.delete(id);
        return "redirect:/admin/topic";
    }

    /*
    * Comment
    * 1. Hiển thị danh sách comment
    */
    @GetMapping("/comment")
    public String comment() {
        return "views_admin/comment";
    }


    /*
    * Comment different
     */
    @GetMapping("/table")
    public String table() { return "views_admin/table"; }

    @GetMapping("/login")
    public String login() {
        return "views_admin/login";
    }

    @GetMapping("/register")
    public String register() {
        return "views_admin/register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "views_admin/forgot-password";
    }

    @GetMapping("/404")
    public String error404() {
        return "views_admin/404";
    }

    @GetMapping("/blank")
    public String blank() {
        return "views_admin/blank";
    }

    @GetMapping("/profile")
    public String profile() {
        return "views_admin/profile";
    }
}
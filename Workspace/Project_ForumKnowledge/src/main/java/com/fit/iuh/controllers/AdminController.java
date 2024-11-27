package com.fit.iuh.controllers;

import com.fit.iuh.entites.Comment;
import com.fit.iuh.entites.Post;
import com.fit.iuh.entites.Topic;
import com.fit.iuh.services.CommentService;
import com.fit.iuh.services.PostService;
import com.fit.iuh.services.TopicService;
import com.fit.iuh.utilities.CommentUtils;
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

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String index() {
        return "index";
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

        return "topic";
    }
    // 2. Thêm topic
    @GetMapping("/topic/addform")
    public String addTopicForm(Model model) {
        model.addAttribute("topic", new Topic());
        return "add-topic";
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

        return "view-topic";
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
    public String comment(Model model,
                          @RequestParam(defaultValue = "0") int numberPage,
                          @RequestParam(defaultValue = "10") int size,
                          @RequestParam(required = false) String key) {

        Page<Post> page;
        if (key != null && !key.trim().isEmpty()) {
            // Nếu có từ khóa tìm kiếm
            page = postService.searchByKeywordWithPaging(key, numberPage, size);
            model.addAttribute("key", key);
        } else {
            page = postService.getPage(numberPage, size);
        }
        model.addAttribute("posts", page.getContent());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", numberPage);
        model.addAttribute("start", numberPage * size + 1);
        model.addAttribute("end", numberPage * size + page.getNumberOfElements());
        model.addAttribute("quantityPost", page.getTotalElements());
        model.addAttribute("size", size);

        List<Comment> comments = new ArrayList<>();
        for (Post post : page.getContent()) {
            comments.addAll(post.getComments());
        }
        // check comment
        int numberNegative = 0;
        for (Comment comment : comments) {
            int check = CommentUtils.checkComment(comment.getContent());
            if (check == 0) {
                numberNegative++;
            }
        }
        model.addAttribute("numberNegative", numberNegative);
        model.addAttribute("comments", comments);

        return "comment";
    }

    @GetMapping("/view-comment/{id}")
    public String viewComment(@PathVariable int id, Model model) {
        Post post = postService.findByID(id);
        model.addAttribute("post", post);
        return "view-comment";
    }

    @GetMapping("/comment/negative")
    public String negativeComment(Model model) {
        return "negative-comment";
    }

    /*
    * Comment different
     */
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
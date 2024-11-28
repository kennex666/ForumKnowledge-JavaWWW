package com.fit.iuh.controllers;

import com.fit.iuh.entites.Post;
import com.fit.iuh.entites.Topic;
import com.fit.iuh.enums.PostState;
import com.fit.iuh.services.PostService;
import com.fit.iuh.services.TopicService;
import com.fit.iuh.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/write_blog_basic")
    public String writeBlog(Model model) {
        Post post = new Post();
        List<Topic> topics = topicService.findAll();
        model.addAttribute("topics", topics);
        model.addAttribute("post", post);
        return "write_blog_basic";
    }
    @PostMapping("/save")
    public String savePost(Post post) {
        Date now = new Date();
//        post.setContent("12313123");
        post.setCreatedAt(now);
        post.setUpdatedAt(now);
//        post.setDescription("Khong co");
        post.setUrl("https://viblo.asia/announcements/khao-sat-viblo-nhu-cau-phat-trien-su-nghiep-it-toan-cau-PAoJePaA41j");
        post.setState(PostState.PUBLISHED);
        post.setTotalComments(1);
        post.setTotalUpVote(1);
        post.setTotalDownVote(0);
        post.setTotalShare(0);
        post.setTotalView(10);
//        post.setTopic(topicService.findById(1));
        post.setAuthor(userService.findById(1));



        postService.save(post);
        return "redirect:/";
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(@RequestParam("keyword") String keyword) {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("status", HttpStatus.OK.value());

        response.put("data", postService.search(keyword));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("id") int id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "post/detail-test";
    }

}
package com.fit.iuh.controllers;

import com.fit.iuh.entites.*;
import com.fit.iuh.services.*;
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

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private BookMarkService bookmarkService;

    @Autowired
    private FollowingService followService;

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
    public String addTopic(Model model,Topic topic) {
        topic.setCreatedAt(new Date(System.currentTimeMillis()));
        topic.setUpdatedAt(new Date(System.currentTimeMillis()));
        // Kiểm tra xem topic đã tồn tại chưa, nếu topic đã tồn { Name , Hashtag } thì thông báo ra
        if (topicService.isExist(topic.getName(), topic.getHashtag())) {
            model.addAttribute("error", "Topic already exists with the same name and hashtag.");
            return "views_admin/add-topic";
        }
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
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", numberPage);
        model.addAttribute("start", numberPage * size + 1);
        model.addAttribute("end", numberPage * size + page.getNumberOfElements());
        model.addAttribute("quantityPost", page.getTotalElements());
        model.addAttribute("size", size);
        model.addAttribute("posts", page.getContent());


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

        List<Post> posts = page.getContent();
        Map<Integer, Integer> negativeCommentCounts = new HashMap<>();
        for (Post post : posts) {
            int countNegative = 0;
            for (Comment comment : post.getComments()) {
                int check = CommentUtils.checkComment(comment.getContent());
                if (check == 0) {
                    countNegative++;
                }
            }
            negativeCommentCounts.put(post.getPostId(), countNegative);
        }
        model.addAttribute("negativeCommentCounts", negativeCommentCounts);

        return "views_admin/comment";
    }

    @GetMapping("/view-comment/{id}")
    public String viewComment(@PathVariable int id, Model model) {
        Post post = postService.findByID(id);
        model.addAttribute("post", post);
        return "views_admin/view-comment";
    }

    @GetMapping("/comment/negative")
    public String negativeComment(Model model) {
        List<Comment> negativeComments = new ArrayList<>();
        List<Post> posts = postService.findAll();
        for (Post post : posts) {
            for (Comment comment : post.getComments()) {
                int check = CommentUtils.checkComment(comment.getContent());
                if (check == 0) {
                    negativeComments.add(comment);
                }
            }
        }
        model.addAttribute("negativeComments", negativeComments);
        return "views_admin/view-negative-comment";
    }

    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable int id) {
        commentService.remove(id);
        return "redirect:/admin/comment";
    }

    /*
    * Dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        
        // Thong ke so luong post 
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);

        // Thong ke so luong post duoc tao trong tuan
        List<Post> postsInWeek = postService.getPostsCreatedInWeek();
        model.addAttribute("postsInWeek", postsInWeek);

        // Thong ke so luong comment, reaction, bookmark, follow trong tuan de lam tong tuong tac
        List<Comment> comments = commentService.findAll();
        List<Reaction> reactions = reactionService.findAll();
        List<BookMark> bookMarks = bookmarkService.findAll();
        List<Following> follows = followService.findAll();

        return "views_admin/index";
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
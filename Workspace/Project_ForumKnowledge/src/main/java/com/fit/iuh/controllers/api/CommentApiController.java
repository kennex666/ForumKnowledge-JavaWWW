package com.fit.iuh.controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fit.iuh.entites.Comment;
import com.fit.iuh.entites.Post;
import com.fit.iuh.entites.Topic;
import com.fit.iuh.enums.PostState;
import com.fit.iuh.repositories.CommentRepository;
import com.fit.iuh.repositories.PostRepository;
import com.fit.iuh.services.CommentService;
import com.fit.iuh.services.PostService;
import com.fit.iuh.services.UserService;
import com.fit.iuh.utilities.OpenAI;
import com.fit.iuh.utilities.SpringContext;
import com.fit.iuh.utilities.StringToUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentApiController {


    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createComment(InputStream inputStream) {
        String currentEmail = SpringContext.getCurrentUserEmail();
        if (currentEmail.isBlank()){
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "You must login to create comment!", "errorCode", 401, "data", ""));
        }
        try {
            String json = new String(inputStream.readAllBytes());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            String content = rootNode.path("content").asText();
            int idPost = rootNode.path("idPost").asInt();

            // Chuyển dữ liệu từ PostDTO vào Post object
            Post post = postService.findById(idPost);
            Comment comment = new Comment();
            comment.setContent(content);

            comment.setPost(post);

            comment.setUsers(userService.findUserByEmail(currentEmail));

            // Trước khi lưu để GPT kiểm tra lại:
//            String jsonOpenAI = OpenAI.autoReview(post.getContent(), "post");
//            System.out.println(jsonOpenAI);
//            Map<String, Object> response = OpenAI.parseJson(jsonOpenAI);
//
//            System.out.println(response.get("status"));
//            if (response.containsKey("status")) {
//                Double status = (Double) response.get("status");
//                String statusCode = status.toString();
//                if (!statusCode.contains("200"))
//                    return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Error: " + response.get("message"), "errorCode", 500, "data", ""));
//            }

            // Lưu bài viết
            commentService.save(comment);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Comment created successfully!", "errorCode", 201, "data", comment));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Error: " + e.getMessage(), "errorCode", 500, "data", ""));
        }
    }
}

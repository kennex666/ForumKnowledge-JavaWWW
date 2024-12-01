package com.fit.iuh.controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fit.iuh.entites.Post;
import com.fit.iuh.entites.Topic;
import com.fit.iuh.entites.User;
import com.fit.iuh.enums.PostState;
import com.fit.iuh.services.PostService;
import com.fit.iuh.services.TopicService;
import com.fit.iuh.services.UserService;
import com.fit.iuh.utilities.OpenAI;
import com.fit.iuh.utilities.SpringContext;
import com.fit.iuh.utilities.StringToUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostAPIController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createPost(InputStream inputStream) {
        String currentEmail = SpringContext.getCurrentUserEmail();
        if (currentEmail.isBlank()){
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "You must login to create post!", "errorCode", 401, "data", null));
        }
        try {
            String message = "";
            String json = new String(inputStream.readAllBytes());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            String title = rootNode.path("title").asText();
            String content = rootNode.path("content").asText();
            String description = rootNode.path("description").asText();
            int tagId = rootNode.path("tag").asInt();
            String tagCustom = rootNode.path("tag_custom").asText();

            if (tagId == -1 && tagCustom.isEmpty())
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Tag is required!", "errorCode", 400, "data", null));
            if (title.isEmpty() || content.isEmpty())
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Title and content are required!", "errorCode", 400, "data", null));
            if (description.isEmpty())
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Description required!", "errorCode", 400, "data", null));

            // Chuyển dữ liệu từ PostDTO vào Post object
            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setDescription(description);
            post.setState(PostState.PUBLISHED);
            post.setTotalComments(0);
            post.setTotalUpVote(0);
            post.setTotalDownVote(0);
            post.setTotalShare(0);
            post.setTotalView(0);
            post.setAuthor(userService.findUserByEmail(currentEmail));

            String url = StringToUrl.convertToUrlFormat(title + "-" + System.currentTimeMillis());

            post.setUrl(url);

            Topic topic = null;
            if (tagId != -1) {
                // Try to find the topic by ID
                topic = topicService.findById(tagId);
            } else {
                topic = new Topic();
                topic.setName(tagCustom);
                topic.setHashtag(StringToUrl.convertToUrlFormat(tagCustom));
                if (topicService.add(topic) == false){
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(Map.of("message", "Error: Cannot create new tag!", "errorCode", 500, "data", ""));
                }
            }

            if (topic == null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(Map.of("message", "Tag not found!", "errorCode", 404, "data", ""));
            }

            post.setTopic(topic);

            // Trước khi lưu để GPT kiểm tra lại:
            String jsonOpenAI = OpenAI.autoReview(post.getContent(), "post");
            System.out.println(jsonOpenAI);
            Map<String, Object> response = OpenAI.parseJson(jsonOpenAI);

            System.out.println(response.get("status"));
            if (response.containsKey("status")) {
                Double status = (Double) response.get("status");
                String statusCode = status.toString();
                if (!statusCode.contains("200"))
                    return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Error: " + response.get("message"), "errorCode", 500, "data", ""));
                message = "Đăng bài thành công, AI đã kiểm tra nội dung!";
            } else {
                message = "Đăng bài thành công, nhưng AI đang bận, không thể kiểm tra nội dung!";
            }

            // Lưu bài viết
            postService.save(post);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", message, "errorCode", 201, "data", post));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Error: " + e.getMessage(), "errorCode", 500, "data", ""));
        }
    }

    // Getpost by ID or URL
    @GetMapping(value = "/{id}")
    public ResponseEntity<Map<String, Object>> getPostById(@PathVariable String id){
        Post post = postService.findByIdAndUrl(id.trim());

        if (post == null){
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Post not found!", "errorCode", 404, "data", ""));
        }

        User author = post.getAuthor();

        if (author == null){
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Author not found!", "errorCode", 404, "data", ""));
        }

        Map authorData = Map.of("id", post.getAuthor().getUserId(), "name", post.getAuthor().getName(), "email", post.getAuthor().getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Post found!", "errorCode", 200, "data", Map.of("post", post, "author", authorData)));
    }

    @PutMapping("/state-change")
    public ResponseEntity<Map<String, Object>> stateChange(@RequestParam("id") int id, @RequestParam("state") PostState state){
        try {
            if (postService.changeState(id, state)) {
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "State changed successfully!", "errorCode", 200, "data", ""));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Error when change state!", "errorCode", 404, "data", ""));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Error: " + e.getMessage(), "errorCode", 500, "data", ""));
        }
    }

}

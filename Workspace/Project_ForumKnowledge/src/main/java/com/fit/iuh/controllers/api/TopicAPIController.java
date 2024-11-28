package com.fit.iuh.controllers.api;

import com.fit.iuh.entites.Topic;
import com.fit.iuh.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/topics")
public class TopicAPIController {

    @Autowired
    public TopicService topicService;

    @GetMapping
    public Map<String, Object> getTags() {
        try {
            List<Topic> topics = topicService.findAll();
            return Map.of("errorCode", "200", "message", "success", "tags", topics);
        } catch (Exception e) {
            return Map.of("errorCode", "500", "message", "error", "tags", "");
        }
    }
}

package com.fit.iuh.controllers.api;

import com.fit.iuh.entites.Topic;
import com.fit.iuh.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TagAPIController {

    @Autowired
    public TopicService topicService;

    @GetMapping("/tags")
    public String getTags() {
        return ;
    }
}

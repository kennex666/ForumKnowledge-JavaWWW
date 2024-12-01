package com.fit.iuh.services.impl;

import com.fit.iuh.entites.Post;
import com.fit.iuh.entites.Topic;
import com.fit.iuh.entites.User;
import com.fit.iuh.enums.PostState;
import com.fit.iuh.repositories.PostRepository;
import com.fit.iuh.repositories.TopicRepository;
import com.fit.iuh.repositories.UserRepository;
import com.fit.iuh.utilities.GeminiContentGenerator;
import com.fit.iuh.utilities.GeminiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostGenerateService {

    private static final Logger logger = LoggerFactory.getLogger(PostGenerateService.class);

    private static final long ONE_DAY = 24 * 60 * 60 * 1000;
    private static final long ONE_MINUTE = 60 * 1000;

    private static final long FIXED_RATE = ONE_MINUTE;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GeminiContentGenerator geminiContentGenerator;

    @Scheduled(fixedRate = FIXED_RATE)
    public void generatePost() {
        logger.info("Scheduled Task - Generating Post: Start");
        checkAndGeneratePost();
    }

    public void checkAndGeneratePost() {
        Date now = new Date();
        Optional<Post> latestPost  = postRepository.findTopByOrderByCreatedAtDesc();
        if (latestPost.isEmpty() || isDiff(latestPost.get().getCreatedAt(), now)) {
            createNewPost();
        } else {
            logger.info("Scheduled Task - Generating Post: Skip");
        }
    }

    private boolean isDiff(Date createdAt, Date now) {
        long diff = now.getTime() - createdAt.getTime();
        logger.info("Scheduled Task - Generating Post: Time difference from last post: {} seconds", diff / 1000);
        return diff >= ONE_MINUTE;
    }

    private void createNewPost() {
        logger.info("Scheduled Task - Generating Post: Begin to generate new post");
        GeminiResponse content = geminiContentGenerator.generateContent();

        if (content == null) {
            logger.error("Scheduled Task - Generating Post: Unable to generate content");
            return;
        }

        Post post = new Post();
        post.setTitle(content.getTitle());
        post.setDescription(content.getDescription());
        post.setContent(content.getContent());
        post.setUrl("");
        post.setState(PostState.PUBLISHED);
        post.setTotalComments(0);
        post.setTotalUpVote(0);
        post.setTotalDownVote(0);
        post.setTotalShare(0);
        post.setTotalView(0);
        post.setCreatedAt(new Date());
        post.setUpdatedAt(new Date());

        List<Topic> topics = topicRepository.findByNameContaining(content.getTopic());
        if (!topics.isEmpty()) {
            post.setTopic(topics.get(0));
        } else {
            logger.error("Scheduled Task - Generating Post: Unable to find topic");
            return;
        }

        List<User> users = userRepository.findByNameContaining("SYSTEM");
        if (!users.isEmpty()) {
            post.setAuthor(users.get(0));
        }

        postRepository.save(post);
        logger.info("Scheduled Task - Generating Post: Success");
    }

}

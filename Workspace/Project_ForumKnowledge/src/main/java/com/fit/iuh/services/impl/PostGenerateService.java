package com.fit.iuh.services.impl;

import com.fit.iuh.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PostGenerateService {

    private static final Logger logger = LoggerFactory.getLogger(PostGenerateService.class);

    private static final long oneDay = 24 * 60 * 60 * 1000;
    private static final long oneMinute = 60 * 1000;

    private static final long FIXED_RATE = oneMinute;

    @Autowired
    private PostService postService;

    @Scheduled(fixedRate = FIXED_RATE)
    public void generatePost() {
        logger.info("Running Scheduled Task: Generating Post");
        postService.checkAndGeneratePost();
    }

}

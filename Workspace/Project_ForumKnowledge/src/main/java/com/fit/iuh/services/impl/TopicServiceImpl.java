package com.fit.iuh.services.impl;

import com.fit.iuh.entites.Topic;
import com.fit.iuh.repositories.TopicRepository;
import com.fit.iuh.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public List<Topic> search(String keyword) { return topicRepository.search(keyword); }
}

package com.fit.iuh.services.impl;

import com.fit.iuh.entites.Topic;
import com.fit.iuh.repositories.TopicRepository;
import com.fit.iuh.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public List<Topic> findByNameContaining(String name) {
        return topicRepository.findByNameContaining(name);
    }

    @Override
    public List<Topic> findByHashtagContaining(String hashtag) {
        return topicRepository.findByHashtagContaining(hashtag);
    }

    @Override
    public boolean existsByHashtag(String hashtag) {
        return topicRepository.existsByHashtag(hashtag);
    }

    @Override
    public List<Topic> findByCreatedAtBetween(Date startDate, Date endDate) {
        return topicRepository.findByCreatedAtBetween(startDate, endDate);
    }

    @Override
    public Topic findById(int id) {
        return topicRepository.findById(id).get();
    }

    @Override
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }
}

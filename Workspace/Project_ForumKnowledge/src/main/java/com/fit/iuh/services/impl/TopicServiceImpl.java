package com.fit.iuh.services.impl;

import com.fit.iuh.entites.Topic;
import com.fit.iuh.repositories.TopicRepository;
import com.fit.iuh.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<Topic> getAll() {
        return topicRepository.findAll();
    }

    @Override
    public Integer getNumberOfPosts(int tagId) {
        return topicRepository.getNumberOfPosts(tagId);
    }

    @Override
    public boolean add(Topic topic) {
        try {
            topicRepository.save(topic);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            topicRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Topic getById(int tagId) {
        return topicRepository.findById(tagId).get();
    }

    @Override
    public boolean update(Topic topic) {
        try {
            topicRepository.save(topic);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Page<Topic> getPage(int page, int size) {
        return topicRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public List<Topic> searchByKeyword(String search) {
        return topicRepository.searchByKeyword(search);
    }

    @Override
    public Page<Topic> searchByKeywordWithPaging(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return topicRepository.findByNameContainingOrHashtagContaining(keyword, keyword, pageable);
    }
    
    public Topic findById(int id) {
        return topicRepository.findById(id).get();
    }

    @Override
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }
}

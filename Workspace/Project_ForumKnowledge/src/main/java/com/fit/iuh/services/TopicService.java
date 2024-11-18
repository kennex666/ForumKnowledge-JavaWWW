package com.fit.iuh.services;

import com.fit.iuh.entites.Topic;

import java.util.List;

public interface TopicService {
    public List<Topic> search(String keyword);
}

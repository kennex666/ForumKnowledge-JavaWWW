package com.fit.iuh.services;

import com.fit.iuh.entites.Topic;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TopicService {
    public List<Topic> findByNameContaining(@Param("name") String name);

    public List<Topic> findByHashtagContaining(@Param("hashtag") String hashtag);

    public boolean existsByHashtag(@Param("hashtag") String hashtag);

    public List<Topic> findByCreatedAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    public List<Topic> getAll();

    Integer getNumberOfPosts(int tagId);
}

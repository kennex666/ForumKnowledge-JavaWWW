package com.fit.iuh.services;

import com.fit.iuh.entites.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TopicService {
    public List<Topic> findByNameContaining(@Param("name") String name);

    public List<Topic> findByHashtagContaining(@Param("hashtag") String hashtag);

    public boolean existsByHashtag(@Param("hashtag") String hashtag);

    public List<Topic> findByCreatedAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    public List<Topic> getAll();

    public Integer getNumberOfPosts(int tagId);

    public boolean add(Topic topic);

    public boolean delete(int id);

    public Topic getById(int tagId);

    public boolean update(Topic topic);

    public Page<Topic> getPage(int page, int size);

    public List<Topic> searchByKeyword(String search);

    public Page<Topic> searchByKeywordWithPaging(String key, int numberPage, int size);
}

package com.fit.iuh.repositories;

import com.fit.iuh.entites.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

@RepositoryRestResource
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    @Query("SELECT t FROM Topic t WHERE t.name LIKE %:name%")
    List<Topic> findByNameContaining(@Param("name") String name);

    @Query("SELECT t FROM Topic t WHERE t.hashtag LIKE %:hashtag%")
    List<Topic> findByHashtagContaining(@Param("hashtag") String hashtag);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Topic t WHERE t.hashtag = :hashtag")
    boolean existsByHashtag(@Param("hashtag") String hashtag);

    @Query("SELECT t FROM Topic t WHERE t.createdAt BETWEEN :startDate AND :endDate")
    List<Topic> findByCreatedAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT count(t) FROM Post t WHERE t.topic.tagId = :tagId")
    Integer getNumberOfPosts(int tagId);
}

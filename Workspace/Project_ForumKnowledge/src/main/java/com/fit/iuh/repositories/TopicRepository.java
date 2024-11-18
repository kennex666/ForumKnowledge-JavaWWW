package com.fit.iuh.repositories;

import com.fit.iuh.entites.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    @Query("SELECT t FROM Topic t WHERE t.name LIKE %:keyword% OR t.hashtag LIKE %:keyword%")
    public List<Topic> search(@Param("keyword") String keyword);
}

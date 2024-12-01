/*
 * Update at 1:45 AM 7/25/2021
 * Author: NguyenThanhLuan
 * Name: Reaction
 * Description: This class is used to store the reaction information
 */
package com.fit.iuh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fit.iuh.entites.Reaction;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@RepositoryRestResource
public interface ReactionRepository extends JpaRepository<Reaction, Integer>{
    // SELECT * FROM reactions r WHERE r.created_at >= DATEADD(day, -7, GETDATE()) ORDER BY r.created_at DESC
    @Query(value = "SELECT * FROM reactions r WHERE r.created_at >= DATEADD(day, -7, GETDATE()) ORDER BY r.created_at DESC", nativeQuery = true)
    List<Reaction> getReactionsCreatedInWeek();

    @Query("SELECT r FROM Reaction r WHERE r.createdAt BETWEEN :startDate AND :endDate")
    List<Reaction> getReactionsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    @Query("SELECT r FROM Reaction r WHERE r.user.id = :userId AND r.post.id = :postId")
    Optional<Reaction> findByUserAndPost(@Param("userId") int userId, @Param("postId") int postId);
    
    @Query("SELECT COUNT(r) FROM Reaction r WHERE r.post.id = :postId AND r.type = 'UPVOTE'")
    int countUpvotesByPostId(@Param("postId") int postId);

    @Query("SELECT COUNT(r) FROM Reaction r WHERE r.post.id = :postId AND r.type = 'DOWNVOTE'")
    int countDownvotesByPostId(@Param("postId") int postId);

    @Query("SELECT r FROM Reaction r WHERE r.post.id = :postId")
    List<Reaction> findByPostId(@Param("postId") int postId);

    @Query("SELECT COUNT(r) FROM Reaction r WHERE r.post.id = :postId")
    int countTotalReactionsByPostId(@Param("postId") int postId);
}

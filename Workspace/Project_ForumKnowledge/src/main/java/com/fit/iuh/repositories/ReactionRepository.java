/*
 * Update at 1:45 AM 7/25/2021
 * Author: NguyenThanhLuan
 * Name: Reaction
 * Description: This class is used to store the reaction information
 */
package com.fit.iuh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fit.iuh.entites.Reaction;

import java.util.Date;
import java.util.List;


@RepositoryRestResource
public interface ReactionRepository extends JpaRepository<Reaction, Integer>{
    // SELECT * FROM reactions r WHERE r.created_at >= DATEADD(day, -7, GETDATE()) ORDER BY r.created_at DESC
    @Query(value = "SELECT * FROM reactions r WHERE r.created_at >= DATEADD(day, -7, GETDATE()) ORDER BY r.created_at DESC", nativeQuery = true)
    List<Reaction> getReactionsCreatedInWeek();

    @Query("SELECT r FROM Reaction r WHERE r.createdAt BETWEEN :startDate AND :endDate")
    List<Reaction> getReactionsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT r FROM Reaction r WHERE r.user.userId = :userId AND r.post.postId = :postId")
    Reaction hasUserVoted(@Param("userId") int userId, @Param("postId") int postId);

    @Modifying
    @Query(value = "INSERT INTO reactions (user_id, post_id, type, created_at, updated_at) VALUES (:userId, :postId, :type, GETDATE(), GETDATE())", nativeQuery = true)
    void createVote(@Param("userId") int userId, @Param("postId") int postId, @Param("type") String type);

    @Modifying
    @Query(value = "UPDATE reactions SET type = :type WHERE user_id = :userId AND post_id = :postId", nativeQuery = true)
    void updateVote(@Param("userId") int userId, @Param("postId") int postId, @Param("type") String type);

    @Modifying
    @Query(value = "DELETE FROM reactions WHERE user_id = :userId AND post_id = :postId", nativeQuery = true)
    void removeVote(@Param("userId") int userId, @Param("postId") int postId);
}

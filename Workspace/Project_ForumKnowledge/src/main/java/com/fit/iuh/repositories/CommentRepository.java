package com.fit.iuh.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fit.iuh.entites.Comment;

@RepositoryRestResource
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	@Query("SELECT c FROM Comment c WHERE c.user.userId  = :userId")
	List<Comment> findAllByUserId(@Param("userId") int userId);
	
    @Query("SELECT c FROM Comment c WHERE c.post.postId = :postId")
    List<Comment> findAllByPostId(@Param("postId") int postId);
    
    @Query("DELETE FROM Comment c WHERE c.commentId = :commentId")
	public Boolean delete(@Param("commentId") int commentId);

    // SELECT * FROM comments c WHERE c.created_at >= DATEADD(day, -7, GETDATE()) ORDER BY c.created_at DESC
    @Query(value = "SELECT * FROM comments c WHERE c.created_at >= DATEADD(day, -7, GETDATE()) ORDER BY c.created_at DESC", nativeQuery = true)
    List<Comment> getCommentsCreatedInWeek();

    @Query("SELECT c FROM Comment c WHERE c.createdAt BETWEEN :startDate AND :endDate")
    List<Comment> getCommentsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}

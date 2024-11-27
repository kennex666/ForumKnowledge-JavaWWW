package com.fit.iuh.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT c FROM Comment c WHERE c.content LIKE %:key%")
    Page<Comment> searchCommentByPostT(String key, Pageable pageable);
}

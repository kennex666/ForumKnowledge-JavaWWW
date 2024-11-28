package com.fit.iuh.services;

import java.util.Date;
import java.util.List;


import com.fit.iuh.entites.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentService {
	public List<Comment> findAll();
	public List<Comment> findAllByUserId(int userId);
	public List<Comment> findAllByPostId(int postId);
	public Comment save(Comment comment);
	public Boolean delete(int commentId);

	public Boolean remove(int id);

	List<Comment> getCommentsCreatedInWeek();

	@Query("SELECT c FROM Comment c WHERE c.createdAt BETWEEN :startDate AND :endDate")
	List<Comment> getCommentsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}

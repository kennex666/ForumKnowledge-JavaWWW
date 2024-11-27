package com.fit.iuh.services;

import java.util.List;


import com.fit.iuh.entites.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {
	public List<Comment> findAll();
	public List<Comment> findAllByUserId(int userId);
	public List<Comment> findAllByPostId(int postId);
	public Comment save(Comment comment);
	public Boolean delete(int commentId);

}

package com.fit.iuh.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fit.iuh.entites.Comment;
import com.fit.iuh.repositories.CommentRepository;
import com.fit.iuh.services.CommentService;

public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public List<Comment> findAll() {
		// TODO Auto-generated method stub
		return commentRepository.findAll();
	}

	@Override
	public List<Comment> findAllByUserId(int userId) {
		// TODO Auto-generated method stub
		return commentRepository.findAllByUserId(userId);
	}

	@Override
	public List<Comment> findAllByPostId(int postId) {
		// TODO Auto-generated method stub
		return commentRepository.findAllByPostId(postId);
	}

	@Override
	public Comment save(Comment comment) {
		// TODO Auto-generated method stub
		return commentRepository.save(comment);
	}

	@Override
	public Boolean delete(int commentId) {
		Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
           commentRepository.delete(commentId);
           return !commentRepository.existsById(commentId);
        }
        return false;
	}
	
}

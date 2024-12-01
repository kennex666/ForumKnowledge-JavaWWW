package com.fit.iuh.services.impl;

import com.fit.iuh.entites.Post;
import com.fit.iuh.entites.Topic;
import com.fit.iuh.entites.User;
import com.fit.iuh.enums.PostState;
import com.fit.iuh.repositories.PostRepository;
import com.fit.iuh.repositories.TopicRepository;
import com.fit.iuh.repositories.UserRepository;
import com.fit.iuh.services.PostService;
import com.fit.iuh.utilities.GeminiContentGenerator;
import com.fit.iuh.utilities.GeminiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> search(String keyword) {
        return postRepository.search(keyword);
    }

	@Override
	public Post save(Post post) {
		// TODO Auto-generated method stub
		return postRepository.save(post);
	}


	@Override
	public Post update(Post post) {
		// TODO Auto-generated method stub
		return postRepository.update(post);
	}
	
	@Override
	public Boolean delete(int postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            postRepository.deleteById(postId);
            return !postRepository.existsById(postId);
        }
        return false;
	}
	
	@Override
	public List<Post> findAll() {
		// TODO Auto-generated method stub
		return postRepository.findAll();
	}
	
	@Override
	public Post findByTitleOrDescrpition(String title, String description) {
		// TODO Auto-generated method stub
		return postRepository.findByTitleOrDescrpition(title, description);
	}

	@Override
	public Post findById(int postId) {
		return postRepository.findById(postId).orElse(null);
	}

	@Override
	public Boolean changeState(int postId, PostState state) {
		Optional<Post> post = postRepository.findById(postId);
		if (post.isPresent()) {
			post.get().setState(state);
			postRepository.save(post.get());
			return true;
		}
		return false;
	}

	@Override
	public Post findTopByOrderByCreatedAtDesc() {
		return postRepository.findTopByOrderByCreatedAtDesc().orElse(null);
	}

	@Override
	public Page<Post> searchByKeywordWithPaging(String key, int numberPage, int size) {
		Pageable pageable = PageRequest.of(numberPage, size);
		return postRepository.searchByKeywordWithPaging(key, pageable);
	}

	@Override
	public Page<Post> getPage(int numberPage, int size) {
		return postRepository.findAll(PageRequest.of(numberPage, size));
	}

	@Override
	public Post findByID(int id) {
		return postRepository.findById(id).orElse(null);
	}

	@Override
	public List<Post> getPostsCreatedInWeek() {
		return postRepository.getPostsCreatedInWeek();
	}

	@Override
	public Post findByUrl(String url) {
		return postRepository.findByUrl(url);
	}

	@Override
	public Post findByIdAndUrl(String id) {
		int postId = -1;
		Post post = null;
		String url = id.trim();
		try {
			postId = Integer.parseInt(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (postId != -1){
			post = postRepository.findById(postId).orElse(null);
		} else {
			post = postRepository.findByUrl(url);
		}
		Collections.reverse(post.getComments());
		return post;
	}

	@Override
	public Page<Post> findForHome(Pageable pageable) {
		return postRepository.findForHome(pageable);
	}

	@Override
	public Page<Post> findPostsWithCondition(int skip, int limit, boolean isDesc) {
		Pageable pageable = PageRequest.of(
				skip - 1,
				limit,
				isDesc ? Sort.by("postId").descending() : Sort.by("postId").ascending()
		);
		return postRepository.findAllPost(pageable);
	}

	@Override
	public Page<Post> findForFollowing(Pageable pageable, int userId) {
		return postRepository.findForFollowing(pageable, userId);
	}


}

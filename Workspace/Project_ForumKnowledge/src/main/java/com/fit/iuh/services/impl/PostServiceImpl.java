package com.fit.iuh.services.impl;

import com.fit.iuh.entites.Post;
import com.fit.iuh.enums.PostState;
import com.fit.iuh.repositories.PostRepository;
import com.fit.iuh.services.PostService;
import com.fit.iuh.utilities.GeminiContentGenerator;
import com.fit.iuh.utilities.GeminiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

	@Autowired
	private GeminiContentGenerator geminiContentGenerator;

	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

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
	public void checkAndGeneratePost() {
		Date now = new Date();
		Optional<Post> latestPost  = postRepository.findTopByOrderByCreatedAtDesc();
		if (latestPost.isEmpty() || isDiff(latestPost.get().getCreatedAt(), now)) {
			createNewPost();
		} else {
			logger.info("Scheduled Task - Generating Post: Skip");
		}
	}

	private boolean isDiff(Date createdAt, Date now) {
		long oneDay = 24 * 60 * 60 * 1000;
		long oneMinute = 60 * 1000;

		long diff = now.getTime() - createdAt.getTime();
        logger.info("Scheduled Task - Generating Post: Time difference from last post: {} seconds", diff / 1000);
		return diff >= oneMinute;
	}

	private void createNewPost() {
		logger.info("Scheduled Task - Generating Post: Begin to generate new post");
		GeminiResponse content = geminiContentGenerator.generateContent();

		if (content == null) {
			logger.error("Scheduled Task - Generating Post: Unable to generate content");
			return;
		}

		Post post = new Post();
		post.setTitle(content.getTitle());
		post.setDescription(content.getDescription());
		post.setContent(content.getContent());
		post.setUrl("");
		post.setState(PostState.PUBLISHED);
		post.setTotalComments(0);
		post.setTotalUpVote(0);
		post.setTotalDownVote(0);
		post.setTotalShare(0);
		post.setTotalView(0);
		post.setCreatedAt(new Date());
		post.setUpdatedAt(new Date());

		postRepository.save(post);
		logger.info("Scheduled Task - Generating Post: Success");
	}

}

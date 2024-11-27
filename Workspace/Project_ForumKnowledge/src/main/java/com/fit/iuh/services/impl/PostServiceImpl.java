package com.fit.iuh.services.impl;

import com.fit.iuh.entites.Post;
import com.fit.iuh.repositories.PostRepository;
import com.fit.iuh.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	   public Post getPostById(int id) {
        return postRepository.findById(id).orElse(null);
    }
}

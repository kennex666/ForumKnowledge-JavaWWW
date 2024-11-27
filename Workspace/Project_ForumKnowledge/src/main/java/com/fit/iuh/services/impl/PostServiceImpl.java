package com.fit.iuh.services.impl;

import com.fit.iuh.entites.Comment;
import com.fit.iuh.entites.Post;
import com.fit.iuh.repositories.PostRepository;
import com.fit.iuh.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

}

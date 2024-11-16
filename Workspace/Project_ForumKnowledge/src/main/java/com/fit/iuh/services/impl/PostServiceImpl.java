package com.fit.iuh.services.impl;

import com.fit.iuh.entites.Post;
import com.fit.iuh.repositories.PostRepository;
import com.fit.iuh.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> search(String keyword) {
        return postRepository.search(keyword);
    }
}

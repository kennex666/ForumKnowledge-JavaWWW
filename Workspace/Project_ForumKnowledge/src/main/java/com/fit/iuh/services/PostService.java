package com.fit.iuh.services;

import com.fit.iuh.entites.Post;

import java.util.List;

public interface PostService {
    public List<Post> search(String keyword);
}

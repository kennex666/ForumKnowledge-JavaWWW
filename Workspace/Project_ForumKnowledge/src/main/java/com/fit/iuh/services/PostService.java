/*
 * Update at 1:45 AM 7/25/2021
 * Author: NguyenThanhLuan
 * Name: Post
 * Description: This class is used to store the post information
 */

package com.fit.iuh.services;

import com.fit.iuh.entites.Comment;
import com.fit.iuh.entites.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    public List<Post> search(String keyword);
    
    // Method post 1 article
    public Post save(Post post);
    
    // Method update article
    public Post update(Post post);
    
    // Method delete
    public Boolean delete(int postId);

    // Method get all article
	public List<Post> findAll();

	// Method find article by title or description
	public Post findByTitleOrDescrpition(String title, String description);

    Page<Post> searchByKeywordWithPaging(String key, int numberPage, int size);

    Page<Post> getPage(int numberPage, int size);

    Post findByID(int id);
}

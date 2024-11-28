/*
 * Update at 1:45 AM 7/25/2021
 * Author: NguyenThanhLuan
 * Name: Post
 * Description: This class is used to store the post information
 */

package com.fit.iuh.services;

import com.fit.iuh.entites.Post;
import com.fit.iuh.enums.PostState;

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

    // Method find article by id
    public Post findById(int postId);

    // Method change state of article
    public Boolean changeState(int postId, PostState state);

    // Method get top article by created date
    public Post findTopByOrderByCreatedAtDesc();

    // Method check and generate article
    public void checkAndGeneratePost();
}

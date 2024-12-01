/*
 * Update at 1:45 AM 7/25/2021
 * Author: NguyenThanhLuan
 * Name: Post
 * Description: This class is used to store the post information
 */

package com.fit.iuh.services;

import com.fit.iuh.entites.Post;
import com.fit.iuh.enums.PostState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    Page<Post> searchByKeywordWithPaging(String key, int numberPage, int size);

    Page<Post> getPage(int numberPage, int size);

    Post findByID(int id);

    List<Post> getPostsCreatedInWeek();
    
    public Post findByUrl(String url);

    public Post findByIdAndUrl(String id);

    // Method get all article
    public Page<Post> findForHome(Pageable pageable);

    public Page<Post> findForFollowing(Pageable pageable, int userId);

    // Method get all article with limit, skip and sort desc
    public Page<Post> findPostsWithCondition(int skip, int limit, boolean isDesc);

}

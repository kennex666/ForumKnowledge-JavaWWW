package com.fit.iuh.repositories;
import com.fit.iuh.entites.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    public List<Post> search(@Param("keyword") String keyword);

    @Query("UPDATE Post p "
    		+ "SET "
    			+ "p.title = :title, p.description = :description, p.content = :content, p.url = :url, p.state = :state, p.totalComments = :totalComments, p.totalUpVote = :totalUpVote, p.totalDownVote = :totalDownVote, p.totalShare = :totalShare, p.totalView = :totalView, p.createdAt = :createdAt, p.updatedAt = :updatedAt, p.postReports = :postReports, p.author = :author, p.reactions = :reactions, p.comments = :comments, p.bookMarks = :bookMarks, p.topic = :topic "
    		+ "WHERE "
    			+ "p.postId = :postId")
	public Post update(@Param("post") Post post);

	@Query("DELETE FROM Post p WHERE p.postId = :postId")
	public Boolean delete(@Param("postId") int postId);

	@Query("SELECT p FROM Post p where p.title LIKE %:title% OR p.description LIKE %:description%")
	public Post findByTitleOrDescrpition(String title, String description);

	public Optional<Post> findTopByOrderByCreatedAtDesc();

	@Query("SELECT p FROM Post p WHERE p.title LIKE %:key% OR p.content LIKE %:key%")
	Page<Post> searchByKeywordWithPaging(String key, Pageable pageable);
}

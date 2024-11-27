package com.fit.iuh.repositories;

import com.fit.iuh.entites.BookMark;
import com.fit.iuh.entites.Comment;
import com.fit.iuh.entites.Post;
import com.fit.iuh.entites.PostReport;
import com.fit.iuh.entites.Reaction;
import com.fit.iuh.entites.Topic;
import com.fit.iuh.entites.User;
import com.fit.iuh.enums.PostState;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
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

}

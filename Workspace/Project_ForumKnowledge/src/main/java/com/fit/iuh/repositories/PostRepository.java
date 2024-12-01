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

	@Query(value = "SELECT * FROM posts p WHERE p.created_at >= DATEADD(day, -7, GETDATE()) ORDER BY p.created_at DESC", nativeQuery = true)
	List<Post> getPostsCreatedInWeek();

	@Query("SELECT p FROM Post p WHERE p.url = :url")
	public Post findByUrl(@Param("url") String url);

	@Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
	public Page<Post> findForHome(Pageable pageable);

	@Query("SELECT p FROM Post p")
	public Page<Post> findAllPost(Pageable pageable);

	@Query("SELECT p FROM Post p JOIN p.author.followers f WHERE f.follower.userId = :userId")
	public Page<Post> findForFollowing(Pageable pageable, int userId);

	@Query("SELECT p FROM Post p WHERE p.author.userId = :userId")
	public Page<Post> findForUser(Pageable pageable, int userId);

	@Query("SELECT p FROM Post p WHERE p.postId = :id")
	public Post findByID(@Param("id") int id);

	//getPostByAuthor
	@Query("SELECT p FROM Post p WHERE p.author.name LIKE %:author%")
	public Page<Post> getPostByAuthor(Pageable pageable, @Param("author") String author);

	//getPostByTopic
	@Query("SELECT p FROM Post p WHERE p.topic.hashtag LIKE %:topic%")
	public Page<Post> getPostByTopic(Pageable pageable, @Param("topic") String topic);

	//searchKeyword
	@Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
	public Page<Post> searchKeyword(Pageable pageable, @Param("keyword") String keyword);

}

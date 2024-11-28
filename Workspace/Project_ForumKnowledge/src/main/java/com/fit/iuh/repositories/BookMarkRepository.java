package com.fit.iuh.repositories;

import com.fit.iuh.entites.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface BookMarkRepository extends JpaRepository<BookMark, Integer> {

    @Query("SELECT b FROM BookMark b WHERE b.user.userId = :userId")
    List<BookMark> findAllByUserId(@Param("userId") int userId);

    @Query("SELECT b FROM BookMark b WHERE b.post.postId = :postId")
    List<BookMark> findAllByPostId(@Param("postId") int postId);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END FROM BookMark b WHERE b.user.userId = :userId AND b.post.postId = :postId")
    boolean existsByUserIdAndPostId(@Param("userId") int userId, @Param("postId") int postId);

    @Query("SELECT COUNT(b) FROM BookMark b WHERE b.post.postId = :postId")
    long countByPostId(@Param("postId") int postId);
    
    BookMark findByUserIdAndPostId(int userId, int postId);
    
    void deleteByPostId(int postId);
    
    List<BookMark> findByPostId(int postId);
}

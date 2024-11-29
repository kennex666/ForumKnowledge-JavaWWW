package com.fit.iuh.repositories;

import com.fit.iuh.entites.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
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

    @Query("SELECT b FROM BookMark b WHERE b.user.userId = :userId AND b.post.postId = :postId")
    BookMark findByUserIdAndPostId(int userId, int postId);

    @Query("DELETE FROM BookMark b WHERE b.post.postId = :postId")
    void deleteByPostId(int postId);

    @Query("SELECT b FROM BookMark b WHERE b.post.postId = :postId")
    List<BookMark> findByPostId(int postId);

    // SELECT * FROM bookmarks b WHERE b.created_at >= DATEADD(day, -7, GETDATE()) ORDER BY b.created_at DESC
    @Query(value = "SELECT * FROM bookmarks b WHERE b.created_at >= DATEADD(day, -7, GETDATE()) ORDER BY b.created_at DESC", nativeQuery = true)
    List<BookMark> getBookMarksCreatedInWeek();

    @Query("SELECT b FROM BookMark b WHERE b.createdAt BETWEEN :startDate AND :endDate")
    List<BookMark> getBookMarksBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}

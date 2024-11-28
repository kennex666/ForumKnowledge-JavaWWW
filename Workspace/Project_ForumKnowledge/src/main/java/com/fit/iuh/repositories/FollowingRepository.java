package com.fit.iuh.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fit.iuh.entites.Following;

@RepositoryRestResource
public interface FollowingRepository extends JpaRepository<Following, Integer>{
	
    @Query("DELETE FROM Following f WHERE f.followId = :followId")
	public Boolean delete(@Param("followId") int followId);

    // SELECT * FROM followings f WHERE f.created_at >= DATEADD(day, -7, GETDATE()) ORDER BY f.created_at DESC
    @Query(value = "SELECT * FROM followings f WHERE f.created_at >= DATEADD(day, -7, GETDATE()) ORDER BY f.created_at DESC", nativeQuery = true)
    List<Following> getFollowingsCreatedInWeek();

    @Query("SELECT f FROM Following f WHERE f.createdAt BETWEEN :startDate AND :endDate")
    List<Following> getFollowingsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}

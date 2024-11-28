package com.fit.iuh.repositories;

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
}

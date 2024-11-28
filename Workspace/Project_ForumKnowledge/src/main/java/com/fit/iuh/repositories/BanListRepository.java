package com.fit.iuh.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fit.iuh.entites.BanList;
import com.fit.iuh.enums.BanListState;

@RepositoryRestResource
public interface BanListRepository extends JpaRepository<BanList, Integer> {
	@Query("SELECT b FROM BanList b WHERE b.state = :state")
	List<BanList> findByState(@Param("state") BanListState state);

	@Query("SELECT b FROM BanList b WHERE b.reason LIKE %:keyword%")
	List<BanList> findByReasonContaining(@Param("keyword") String keyword);

	@Query("SELECT b FROM BanList b WHERE b.timeStart BETWEEN :startDate AND :endDate")
	List<BanList> findByTimeStartBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT b FROM BanList b WHERE b.timeEnd < :endDate")
	List<BanList> findByTimeEndBefore(@Param("endDate") Date endDate);

	@Query("SELECT b FROM BanList b WHERE b.user.id = :userId")
	List<BanList> findByUserId(@Param("userId") Integer userId);
}

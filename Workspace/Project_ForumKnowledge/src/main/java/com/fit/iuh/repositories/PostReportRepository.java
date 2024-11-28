package com.fit.iuh.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fit.iuh.entites.PostReport;
import com.fit.iuh.enums.PostReportState;

@RepositoryRestResource
public interface PostReportRepository extends JpaRepository<PostReport, Integer>{
	@Query("SELECT pr FROM PostReport pr WHERE pr.state = :state")
    public List<PostReport> findByState(@Param("state") PostReportState state);

    @Query("SELECT pr FROM PostReport pr WHERE pr.reason LIKE %:keyword%")
    public List<PostReport> findByReasonContaining(@Param("keyword") String keyword);

    @Query("SELECT pr FROM PostReport pr WHERE pr.createdAt BETWEEN :startDate AND :endDate")
    List<PostReport> findByCreateAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT pr FROM PostReport pr WHERE pr.reporter.userId = :reporterId")
    public List<PostReport> findByReporterId(@Param("reporterId") Integer reporterId);

    @Query("SELECT pr FROM PostReport pr WHERE pr.inspector.userId = :inspectorId")
    public List<PostReport> findByInspectorId(@Param("inspectorId") Integer inspectorId);

}

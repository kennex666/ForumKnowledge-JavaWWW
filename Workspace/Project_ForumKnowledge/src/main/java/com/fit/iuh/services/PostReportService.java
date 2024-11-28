package com.fit.iuh.services;

import com.fit.iuh.entites.PostReport;
import com.fit.iuh.enums.PostReportState;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PostReportService {
    public List<PostReport> getAllPostReports();

    public Optional<PostReport> getPostReportById(int id);

    public PostReport saveOrUpdatePostReport(PostReport postReport);

    public void deletePostReport(int id);

    public List<PostReport> findByState(PostReportState state);

	public List<PostReport> searchByReason(String keyword);

	public List<PostReport> findByCreatedAtBetween(Date startDate, Date endDate);
}

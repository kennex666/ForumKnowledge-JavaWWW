package com.fit.iuh.services.impl;

import com.fit.iuh.entites.PostReport;
import com.fit.iuh.enums.PostReportState;
import com.fit.iuh.repositories.PostReportRepository;
import com.fit.iuh.services.PostReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostReportServiceImpl implements PostReportService {
    @Autowired
    private PostReportRepository postReportRepository;

    @Override
    public List<PostReport> getAllPostReports() {
        return postReportRepository.findAll();
    }

    @Override
    public Optional<PostReport> getPostReportById(int id) {
        return postReportRepository.findById(id);
    }

    @Override
    public PostReport saveOrUpdatePostReport(PostReport postReport) {
        postReport.setUpdatedAt(new Date());
        return postReportRepository.save(postReport);
    }

    @Override
    public void deletePostReport(int id) {
        postReportRepository.deleteById(id);
    }

    @Override
    public List<PostReport> findByState(PostReportState state) {
        return postReportRepository.findByState(state);
    }

    @Override
    public List<PostReport> searchByReason(String keyword) {
        return postReportRepository.findByReasonContaining(keyword);
    }

	@Override
	public List<PostReport> findByCreatedAtBetween(Date startDate, Date endDate) {
		return postReportRepository.findByCreateAtBetween(startDate, endDate);
	}

    @Override
    public List<PostReport> findAll() {
        return postReportRepository.findAll();
    }

    @Override
    public List<PostReport> getPostReportsCreatedInWeek() {
        return postReportRepository.getPostReportsCreatedInWeek();
    }

    @Override
    public List<PostReport> findByReporterId(int reporterId) {
        return postReportRepository.findByReporterId(reporterId);
    }

    @Override
    public List<PostReport> findByInspectorId(int inspectorId) {
        return postReportRepository.findByInspectorId(inspectorId);
    }

    @Override
    public List<PostReport> findByPostId(int postId) {
        return postReportRepository.findByPostId(postId);
    }


}

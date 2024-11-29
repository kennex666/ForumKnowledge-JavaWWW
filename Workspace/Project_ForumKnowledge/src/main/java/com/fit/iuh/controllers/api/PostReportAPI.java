package com.fit.iuh.controllers.api;

import com.fit.iuh.entites.PostReport;
import com.fit.iuh.enums.PostReportState;
import com.fit.iuh.services.PostReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post-reports")
public class PostReportAPI {

    @Autowired
    private PostReportService postReportService;

    @GetMapping("/by-post-id/{postId}")
    public ResponseEntity<Map<String, Object>> getPostReportsByPostId(@PathVariable("postId") int postId) {
        try {
            List<PostReport> postReports = postReportService.findByPostId(postId);
            PostReportState[] postReportStates = PostReportState.values();

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("body", postReports);
            response.put("postReportStates", postReportStates);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}

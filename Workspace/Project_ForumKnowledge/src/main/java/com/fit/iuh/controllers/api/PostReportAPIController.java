package com.fit.iuh.controllers.api;

import com.fit.iuh.entites.PostReport;
import com.fit.iuh.enums.PostReportState;
import com.fit.iuh.services.PostReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post-reports")
public class PostReportAPIController {

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

    @PutMapping("/state-change")
    public ResponseEntity<Map<String, Object>> changeState(@RequestParam("id") int id, @RequestParam("state") PostReportState state) {
        try {
            if (postReportService.changeState(id, state)) {
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "State changed successfully!", "errorCode", 200, "data", ""));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "State change failed!", "errorCode", 400, "data", ""));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error: " + e.getMessage(), "errorCode", 500, "data", ""));
        }
    }

}

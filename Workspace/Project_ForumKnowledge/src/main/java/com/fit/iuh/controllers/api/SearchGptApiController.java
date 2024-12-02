package com.fit.iuh.controllers.api;

import com.fit.iuh.utilities.OpenAI;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search-gpt")
public class SearchGptApiController {
    @GetMapping
    public ResponseEntity searchGpt(@Param("q") String q) {

        String gptContent = OpenAI.generateContent(q);

        return ResponseEntity.ok(Map.of("data", gptContent, "status", 200));
    }
}

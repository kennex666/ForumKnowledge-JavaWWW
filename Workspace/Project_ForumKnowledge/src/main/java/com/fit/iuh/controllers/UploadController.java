package com.fit.iuh.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "*") // Allow all origins for CORS
public class UploadController {
    private static final String UPLOAD_DIR = "uploads/";

    @Value("${server.port}")
    private int serverPort;

    // Create the `uploads` directory if it doesn't exist
    public UploadController() {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "No file uploaded");
            response.put("errorCode", "500");
            return ResponseEntity.ok(response);
        }

        try {
            // Tạo tên tệp ngẫu nhiên bằng UUID
            String originalFileName = file.getOriginalFilename();
            String fileExtension = ""; // Phần mở rộng của tệp (ví dụ .jpg, .png)
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String randomFileName = UUID.randomUUID().toString() + fileExtension;

            // Lưu tệp với tên ngẫu nhiên
            Path path = Paths.get(UPLOAD_DIR + randomFileName);
            Files.write(path, file.getBytes());

            // Construct the file URL
            String fileUrl = "http://localhost:" + serverPort + "/uploads/" + randomFileName;

            // Return the URL in JSON format
            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);
            response.put("errorCode", "201");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "File upload failed: " + e.toString());
            response.put("errorCode", "500");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

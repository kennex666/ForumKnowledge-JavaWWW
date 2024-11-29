package com.fit.iuh.controllers;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fit.iuh.entites.BookMark;
import com.fit.iuh.entites.Post;
import com.fit.iuh.entites.User;
import com.fit.iuh.services.BookMarkService;
import com.fit.iuh.services.UserService;
import com.fit.iuh.utilities.SpringContext;

@RestController
@RequestMapping("/api/bookmarks")
public class BookMarkController {
    
    @Autowired
    private BookMarkService bookMarkService;
    
    @Autowired
    private UserService userService;

    @PostMapping("/toggle")
    public ResponseEntity<String> toggleBookmark(@RequestBody Map<String, Integer> requestData) {
        try {
            String currentEmail = SpringContext.getCurrentUserEmail();
            User currentUser = userService.findUserByEmail(currentEmail);
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }

            int postId = requestData.get("postId");
            boolean exists = bookMarkService.existsByUserIdAndPostId(currentUser.getUserId(), postId);

            if (exists) {
                bookMarkService.deleteByUserIdAndPostId(currentUser.getUserId(), postId);
                return ResponseEntity.ok("Bookmark removed");
            } else {
                BookMark bookmark = new BookMark();
                bookmark.setUser(currentUser);
                bookmark.setPost(new Post(postId)); 
                bookmark.setCreatedAt(new Date());
                bookmark.setUpdatedAt(new Date());

                bookMarkService.save(bookmark);
                return ResponseEntity.ok("Bookmark added");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}

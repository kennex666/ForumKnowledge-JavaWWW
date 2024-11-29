package com.fit.iuh.controllers;


import com.fit.iuh.entites.BookMark;
import com.fit.iuh.services.BookMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
public class BookMarkController {

    @Autowired
    private BookMarkService bookMarkService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookMark>> getBookmarksByUserId(@PathVariable int userId) {
        List<BookMark> bookmarks = bookMarkService.findAllByUserId(userId);
        return ResponseEntity.ok(bookmarks);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<BookMark>> getBookmarksByPostId(@PathVariable int postId) {
        List<BookMark> bookmarks = bookMarkService.findAllByPostId(postId);
        return ResponseEntity.ok(bookmarks);
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkBookmarkExists(@RequestParam int userId, @RequestParam int postId) {
        boolean exists = bookMarkService.existsByUserIdAndPostId(userId, postId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/count/{postId}")
    public ResponseEntity<Long> countBookmarksByPostId(@PathVariable int postId) {
        long count = bookMarkService.countByPostId(postId);
        return ResponseEntity.ok(count);
    }

    @PostMapping
    public ResponseEntity<BookMark> saveBookmark(@RequestBody BookMark bookMark) {
        BookMark savedBookmark = bookMarkService.save(bookMark);
        return ResponseEntity.ok(savedBookmark);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBookmark(@RequestParam int userId, @RequestParam int postId) {
        bookMarkService.deleteByUserIdAndPostId(userId, postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> removeBookmark(@PathVariable int postId) {
        bookMarkService.removeBookmark(postId);
        return ResponseEntity.ok().build();
    }
}

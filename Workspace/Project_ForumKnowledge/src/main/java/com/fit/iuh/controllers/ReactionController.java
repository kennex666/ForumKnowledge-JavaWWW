package com.fit.iuh.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fit.iuh.entites.Post;
import com.fit.iuh.entites.Reaction;
import com.fit.iuh.entites.User;
import com.fit.iuh.enums.ReactionType;
import com.fit.iuh.services.ReactionService;
import com.fit.iuh.services.UserService;
import com.fit.iuh.utilities.SpringContext;

@RestController
@RequestMapping("/api/reactions")
public class ReactionController {
    @Autowired
    private ReactionService reactionService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/vote")
    public ResponseEntity<?> vote(@RequestBody Map<String, Object> payload) {
        String currentEmail = SpringContext.getCurrentUserEmail();
        User currentUser = userService.findUserByEmail(currentEmail);
        
        int postId = (int) payload.get("postId");
        String type = (String) payload.get("type");
        ReactionType reactionType = ReactionType.valueOf(type);
        
        Reaction reaction = reactionService.findByUserAndPost(currentUser.getUserId(), postId);
        
        Map<String, Object> response = new HashMap<>();
        
        if (reaction != null) {
            if (reaction.getType() == reactionType) {
                response.put("success", false);
                response.put("message", "You have already performed this action!");
                return ResponseEntity.ok(response);
            } else {
                reaction.setType(reactionType);
                reactionService.update(reaction);
            }
        } else {
            Reaction newReaction = new Reaction();
            newReaction.setType(reactionType);
            newReaction.setPost(new Post(postId));
            newReaction.setUser(currentUser);
            reactionService.save(newReaction);
        }
        int upvoteCount = reactionService.countUpvotes(postId);
        int downvoteCount = reactionService.countDownvotes(postId);
        
        response.put("success", true);
        response.put("message", "Reaction saved!");
        response.put("upvoteCount", upvoteCount);
        response.put("downvoteCount", downvoteCount);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{postId}/reactions")
    public ResponseEntity<?> getPostReactions(@PathVariable int postId) {
        String currentEmail = SpringContext.getCurrentUserEmail();
        User currentUser = userService.findUserByEmail(currentEmail);
        
        // Get reaction counts
        int upvoteCount = reactionService.countUpvotes(postId);
        int downvoteCount = reactionService.countDownvotes(postId);
        
        Reaction currentUserReaction = reactionService.findByUserAndPost(currentUser.getUserId(), postId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("upvoteCount", upvoteCount);
        response.put("downvoteCount", downvoteCount);
        response.put("currentUserReaction", currentUserReaction != null ? currentUserReaction.getType().name() : null);
        
        return ResponseEntity.ok(response);
    }
}
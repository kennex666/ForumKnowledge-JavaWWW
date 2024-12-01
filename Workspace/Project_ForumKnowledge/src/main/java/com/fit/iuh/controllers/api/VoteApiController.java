package com.fit.iuh.controllers.api;

import com.fit.iuh.entites.Reaction;
import com.fit.iuh.entites.User;
import com.fit.iuh.enums.ReactionType;
import com.fit.iuh.services.PostService;
import com.fit.iuh.services.ReactionService;
import com.fit.iuh.services.UserService;
import com.fit.iuh.utilities.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/vote")
public class VoteApiController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReactionService reactionService;

    @GetMapping("/{postId}")
    public ResponseEntity votePost(@PathVariable int postId, @RequestParam int type) {
        // type = 1: upvote, type = 0: downvote
        // Get current user
        User currentUser = userService.findUserByEmail(SpringContext.getCurrentUserEmail());
        ReactionType userReact = type == 1 ? ReactionType.UPVOTE : ReactionType.DOWNVOTE;
        try {
            // Check if user has already voted
            Reaction reaction = reactionService.hasUserVoted(currentUser.getUserId(), postId);
            if (reaction != null) {
                if (reaction.getType() == userReact) {
                    // Remove
                    reactionService.removeVote(currentUser.getUserId(), postId);
                    return ResponseEntity.ok().body(Map.of("errorCode", 200, "message", "Remove successfully"));
                } else
                    // If user has already voted, update the vote
                    reactionService.updateVote(currentUser.getUserId(), postId, userReact.toString());
            } else {
                // If user has not voted, create a new vote
                reactionService.createVote(currentUser.getUserId(), postId, userReact.toString());
            }
            return ResponseEntity.ok().body(Map.of("errorCode", 200, "message", "Vote successfully"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("errorCode", 400, "message", "Vote failed"));
        }
    }
}

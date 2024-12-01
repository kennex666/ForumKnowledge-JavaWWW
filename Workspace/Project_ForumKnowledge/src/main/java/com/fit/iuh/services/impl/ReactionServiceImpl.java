package com.fit.iuh.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fit.iuh.entites.Post;
import com.fit.iuh.entites.Reaction;
import com.fit.iuh.repositories.ReactionRepository;
import com.fit.iuh.services.ReactionService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReactionServiceImpl implements ReactionService{
	@Autowired
	private ReactionRepository reactionRepository;

	@Override
	public List<Reaction> findAll() {
		// TODO Auto-generated method stub
		return reactionRepository.findAll();
	}

	@Override
	public Boolean save(Reaction reaction) {
		// TODO Auto-generated method stub
		return reactionRepository.save(reaction) != null;
	}

	@Override
	public Boolean update(Reaction reaction) {
		// TODO Auto-generated method stub
		return reactionRepository.save(reaction) != null;
	}

    @Override
    public List<Reaction> getReactionsCreatedInWeek() {
        return reactionRepository.getReactionsCreatedInWeek();
    }

	@Override
	public List<Reaction> getReactionsBetweenDates(Date startDate, Date endDate) {
		return reactionRepository.getReactionsBetweenDates(startDate, endDate);
	}

	@Override
    public void delete(Reaction reaction) {
        reactionRepository.delete(reaction);
    }

	@Override
	public Reaction findByUserAndPost(int userId, int postId) {
		return reactionRepository.findByUserAndPost(userId, postId).orElse(null);
	}

	@Override
    public int countUpvotes(int postId) {
        return reactionRepository.countUpvotesByPostId(postId);
    }

	@Override
    public int countDownvotes(int postId) {
        return reactionRepository.countDownvotesByPostId(postId);
    }

	@Override
    public int countTotalReactions(int postId) {
        return reactionRepository.countTotalReactionsByPostId(postId);
    }

	@Override
    public List<Reaction> getReactionsByPost(int postId) {
        return reactionRepository.findByPostId(postId);
    }

}

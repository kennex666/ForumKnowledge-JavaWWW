package com.fit.iuh.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fit.iuh.entites.Reaction;
import com.fit.iuh.repositories.ReactionRepository;
import com.fit.iuh.services.ReactionService;

import java.util.Date;
import java.util.List;

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
	public Reaction hasUserVoted(int userId, int postId) {
		return reactionRepository.hasUserVoted(userId, postId);
	}

	@Transactional
	@Override
	public void createVote(int userId, int postId, String type) {
		reactionRepository.createVote(userId, postId, type);
	}

	@Transactional
	@Override
	public void updateVote(int userId, int postId, String type) {
		reactionRepository.updateVote(userId, postId, type);
	}

	@Transactional
	@Override
	public void removeVote(int userId, int postId) {
		reactionRepository.removeVote(userId, postId);
	}
}

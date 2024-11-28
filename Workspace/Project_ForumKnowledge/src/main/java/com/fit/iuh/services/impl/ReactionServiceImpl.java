package com.fit.iuh.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fit.iuh.entites.Reaction;
import com.fit.iuh.repositories.ReactionRepository;
import com.fit.iuh.services.ReactionService;

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
}

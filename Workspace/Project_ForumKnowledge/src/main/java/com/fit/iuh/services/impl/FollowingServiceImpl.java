package com.fit.iuh.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fit.iuh.entites.Following;
import com.fit.iuh.repositories.FollowingRepository;
import com.fit.iuh.services.FollowingService;
import org.springframework.stereotype.Service;

@Service
public class FollowingServiceImpl implements FollowingService{
	@Override
	public List<Following> getFollowingsBetweenDates(Date startDate, Date endDate) {
		return followingRepository.getFollowingsBetweenDates(startDate, endDate);
	}

	@Autowired
	private FollowingRepository followingRepository;
	
	@Override
	public List<Following> findAll() {
		// TODO Auto-generated method stub
		return followingRepository.findAll();
	}

	@Override
	public Following save(Following following) {
		// TODO Auto-generated method stub
		return followingRepository.save(following);
	}

	@Override
	public Boolean delete(int followId) {
		Optional<Following> following = followingRepository.findById(followId);
		if(following.isPresent()) {
			followingRepository.delete(followId);
			return !followingRepository.existsById(followId);
		}
		return false;
	}

	@Override
	public List<Following> getFollowingsCreatedInWeek() {
		return followingRepository.getFollowingsCreatedInWeek();
	}

}

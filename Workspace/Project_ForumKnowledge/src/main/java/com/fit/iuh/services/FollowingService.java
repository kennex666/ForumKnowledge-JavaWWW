package com.fit.iuh.services;

import java.util.Date;
import java.util.List;


import com.fit.iuh.entites.Following;

public interface FollowingService {
	public List<Following> findAll();
	public Following save(Following following);
	public Boolean delete(int followId);

	List<Following> getFollowingsCreatedInWeek();


	List<Following> getFollowingsBetweenDates(Date startDate, Date endDate);
}

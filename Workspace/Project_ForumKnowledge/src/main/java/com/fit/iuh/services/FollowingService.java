package com.fit.iuh.services;

import java.util.List;


import com.fit.iuh.entites.Following;

public interface FollowingService {
	public List<Following> findAll();
	public Following save(Following following);
	public Boolean delete(int followId);

}

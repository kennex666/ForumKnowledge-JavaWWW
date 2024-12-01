/**
 * Update at 1:45 AM 7/25/2021
 * Author: NguyenThanhLuan
 * Name: ReactionService
 */
package com.fit.iuh.services;

import java.util.Date;
import java.util.List;

import com.fit.iuh.entites.Reaction;

public interface ReactionService {
	public List<Reaction> findAll();
	public Boolean save(Reaction reaction);
	public Boolean update(Reaction reaction);

    List<Reaction> getReactionsCreatedInWeek();

	List<Reaction> getReactionsBetweenDates(Date startDate, Date endDate);

	Reaction hasUserVoted(int userId, int postId);

	void createVote(int userId, int postId, String type);

	void updateVote(int userId, int postId, String type);

	void removeVote(int userId, int postId);
}

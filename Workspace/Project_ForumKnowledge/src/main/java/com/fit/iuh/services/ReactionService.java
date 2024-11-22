/**
 * Update at 1:45 AM 7/25/2021
 * Author: NguyenThanhLuan
 * Name: ReactionService
 */
package com.fit.iuh.services;

import java.util.List;

import com.fit.iuh.entites.Reaction;

public interface ReactionService {
	public List<Reaction> findAll();
	public Boolean save(Reaction reaction);
	public Boolean update(Reaction reaction);
}

/*
 * Update at 1:45 AM 7/25/2021
 * Author: NguyenThanhLuan
 * Name: Reaction
 * Description: This class is used to store the reaction information
 */
package com.fit.iuh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fit.iuh.entites.Reaction;


@RepositoryRestResource
public interface ReactionRepository extends JpaRepository<Reaction, Integer>{
	
}

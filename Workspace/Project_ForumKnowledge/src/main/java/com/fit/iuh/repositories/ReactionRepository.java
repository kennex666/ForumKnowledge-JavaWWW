package com.fit.iuh.repositories;

import java.util.Date;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
/*
 * Update at 1:45 AM 7/25/2021
 * Author: NguyenThanhLuan
 * Name: Reaction
 * Description: This class is used to store the reaction information
 */

import com.fit.iuh.entites.Post;
import com.fit.iuh.entites.User;
import com.fit.iuh.enums.ReactionType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@RepositoryRestResource
public interface ReactionRepository {
}

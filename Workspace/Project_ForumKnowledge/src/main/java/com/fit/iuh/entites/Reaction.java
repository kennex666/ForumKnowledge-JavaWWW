package com.fit.iuh.entites;

import java.util.Date;
import java.util.Objects;

import com.fit.iuh.enums.ReactionType;

public class Reaction {
	private int reactionId;

	private ReactionType type;

	private Date createdAt;

	private Date updatedAt;

	private User user;

	private Post post;

	public int getReactionId() {
		return reactionId;
	}

	public void setReactionId(int reactionId) {
		this.reactionId = reactionId;
	}

	public ReactionType getType() {
		return type;
	}

	public void setType(ReactionType type) {
		this.type = type;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Reaction(int reactionId, ReactionType type, Date createdAt, Date updatedAt, User user, Post post) {
		super();
		this.reactionId = reactionId;
		this.type = type;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.user = user;
		this.post = post;
	}

	public Reaction(int reactionId, ReactionType type, Date createdAt, Date updatedAt) {
		super();
		this.reactionId = reactionId;
		this.type = type;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Reaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return type + "";
	}

	@Override
	public int hashCode() {
		return Objects.hash(reactionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reaction other = (Reaction) obj;
		return reactionId == other.reactionId;
	}

}

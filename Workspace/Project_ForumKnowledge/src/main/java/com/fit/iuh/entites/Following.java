package com.fit.iuh.entites;

import java.util.Date;
import java.util.Objects;

public class Following {
	private int followId;

	private Date createdAt;

	private Date updatedAt;

	private User follower;

	private User followed;

	public int getFollowId() {
		return followId;
	}

	public void setFollowId(int followId) {
		this.followId = followId;
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

	public User getFollower() {
		return follower;
	}

	public void setFollower(User follower) {
		this.follower = follower;
	}

	public User getFollowed() {
		return followed;
	}

	public void setFollowed(User followed) {
		this.followed = followed;
	}

	public Following(int followId, Date createdAt, Date updatedAt, User follower, User followed) {
		super();
		this.followId = followId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.follower = follower;
		this.followed = followed;
	}

	public Following(int followId, Date createdAt, Date updatedAt) {
		super();
		this.followId = followId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Following() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return followId + "";
	}

	@Override
	public int hashCode() {
		return Objects.hash(followId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Following other = (Following) obj;
		return followId == other.followId;
	}

}
package com.fit.iuh.entites;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="followings")
@EntityListeners(AuditingEntityListener.class)  // Kích hoạt Auditing cho entity này
public class Following {

	@Id
	@Column(name="follow_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int followId;

	@Column(name="created_at", nullable = false, unique = false, columnDefinition = "")
	@CreatedDate
	private Date createdAt;

	@Column(name="updated_at", nullable = false, unique = false, columnDefinition = "")
	@LastModifiedDate
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="follower_id", referencedColumnName = "user_id")
	private User follower;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="followed_id", referencedColumnName = "user_id")
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
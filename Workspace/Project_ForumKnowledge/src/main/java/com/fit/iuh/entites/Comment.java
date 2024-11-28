package com.fit.iuh.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fit.iuh.utilities.DateFormat;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.Objects;


@Entity
@Table(name="comments")
@EntityListeners(AuditingEntityListener.class)  // Kích hoạt Auditing cho entity này
public class Comment {

	@Id
	@Column(name="comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int commentId;

	@Column(name="content", nullable = false, unique = false, columnDefinition = "")
	private String content;

	@Column(name="created_at", nullable = false, unique = false, columnDefinition = "")
	@CreatedDate
	private Date createdAt;

	@Column(name="updated_at", nullable = false, unique = false, columnDefinition = "")
	@LastModifiedDate
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id", referencedColumnName = "user_id")
	private User user;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="post_id", referencedColumnName = "post_id")
	private Post post;

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public void setUsers(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}


	public String getDateFormat(){
		return DateFormat.formatMMMMddyyyy(createdAt != null ? createdAt : new Date());
	}

	public String getDateTimeFormat(){
		return DateFormat.formatHHmmssMMMMddyyyy(createdAt != null ? createdAt : new Date());
	}

	public Comment(int commentId, String content, Date createdAt, Date updatedAt) {
		super();
		this.commentId = commentId;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Comment(int commentId, String content, Date createdAt, Date updatedAt, User user, Post post) {
		super();
		this.commentId = commentId;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.user = user;
		this.post = post;
	}

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return content;
	}

	@Override
	public int hashCode() {
		return Objects.hash(commentId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		return commentId == other.commentId;
	}

}
package com.fit.iuh.entites;

import java.util.Date;
import java.util.Objects;

import com.fit.iuh.enums.PostReportState;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="post_reports")
@EntityListeners(AuditingEntityListener.class)  // Kích hoạt Auditing cho entity này
public class PostReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Enumerated(EnumType.STRING)
	@Column(name="state", nullable = false, unique = false, columnDefinition = "")
	private PostReportState state;

	@Column(name="reason", nullable = false, unique = false, columnDefinition = "")
	private String reason;

	@Column(name="created_at", nullable = false, unique = false, columnDefinition = "")
	private Date createdAt;

	@Column(name="updated_at", nullable = false, unique = false, columnDefinition = "")
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="post_id", referencedColumnName = "post_id")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="reporter_id", referencedColumnName = "user_id")
	private User reporter;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="inspector_id", nullable = true)
	private User inspector;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PostReportState getState() {
		return state;
	}

	public void setState(PostReportState state) {
		this.state = state;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getReporter() {
		return reporter;
	}

	public void setReporter(User reporter) {
		this.reporter = reporter;
	}

	public User getInspector() {
		return inspector;
	}

	public void setInspector(User inspector) {
		this.inspector = inspector;
	}

	public PostReport(int id, PostReportState state, String reason, Date createdAt, Date updatedAt, Post post,
			User reporter, User inspector) {
		super();
		this.id = id;
		this.state = state;
		this.reason = reason;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.post = post;
		this.reporter = reporter;
		this.inspector = inspector;
	}

	public PostReport(int id, PostReportState state, String reason, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.state = state;
		this.reason = reason;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public PostReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return id + "";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostReport other = (PostReport) obj;
		return id == other.id;
	}

}

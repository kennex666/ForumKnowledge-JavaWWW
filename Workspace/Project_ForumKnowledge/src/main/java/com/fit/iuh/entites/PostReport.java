package com.fit.iuh.entites;

import java.util.Date;
import java.util.Objects;

import com.fit.iuh.enums.PostReportState;

public class PostReport {
	private int id;

	private PostReportState state;

	private String reason;

	private Date createAt;

	private Date updateAt;

	private Post post;

	private User reporter;

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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
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

	public PostReport(int id, PostReportState state, String reason, Date createAt, Date updateAt, Post post,
			User reporter, User inspector) {
		super();
		this.id = id;
		this.state = state;
		this.reason = reason;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.post = post;
		this.reporter = reporter;
		this.inspector = inspector;
	}

	public PostReport(int id, PostReportState state, String reason, Date createAt, Date updateAt) {
		super();
		this.id = id;
		this.state = state;
		this.reason = reason;
		this.createAt = createAt;
		this.updateAt = updateAt;
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

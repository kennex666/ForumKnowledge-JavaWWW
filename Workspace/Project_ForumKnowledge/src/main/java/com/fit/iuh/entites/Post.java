package com.fit.iuh.entites;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Post {
	private int postId;

	private String title;

	private String description;

	private String content;

	private String url;

	private String state;

	private int totalComments;

	private int totalUpVote;

	private int totalDownVote;

	private int totalShare;

	private int totalView;

	private Date createdAt;

	private Date updatedAt;

	private List<PostReport> postReports;

	private User author;

	private List<Reaction> reactions;

	private List<Comment> comments;

	private List<BookMark> bookMarks;

	private List<Topic> topics;

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getTotalComments() {
		return totalComments;
	}

	public void setTotalComments(int totalComments) {
		this.totalComments = totalComments;
	}

	public int getTotalUpVote() {
		return totalUpVote;
	}

	public void setTotalUpVote(int totalUpVote) {
		this.totalUpVote = totalUpVote;
	}

	public int getTotalDownVote() {
		return totalDownVote;
	}

	public void setTotalDownVote(int totalDownVote) {
		this.totalDownVote = totalDownVote;
	}

	public int getTotalShare() {
		return totalShare;
	}

	public void setTotalShare(int totalShare) {
		this.totalShare = totalShare;
	}

	public int getTotalView() {
		return totalView;
	}

	public void setTotalView(int totalView) {
		this.totalView = totalView;
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

	public List<PostReport> getPostReports() {
		return postReports;
	}

	public void setPostReports(List<PostReport> postReports) {
		this.postReports = postReports;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(List<Reaction> reactions) {
		this.reactions = reactions;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<BookMark> getBookMarks() {
		return bookMarks;
	}

	public void setBookMarks(List<BookMark> bookMarks) {
		this.bookMarks = bookMarks;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public Post(int postId, String title, String description, String content, String url, String state,
			int totalComments, int totalUpVote, int totalDownVote, int totalShare, int totalView, Date createdAt,
			Date updatedAt) {
		super();
		this.postId = postId;
		this.title = title;
		this.description = description;
		this.content = content;
		this.url = url;
		this.state = state;
		this.totalComments = totalComments;
		this.totalUpVote = totalUpVote;
		this.totalDownVote = totalDownVote;
		this.totalShare = totalShare;
		this.totalView = totalView;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Post(int postId, String title, String description, String content, String url, String state,
			int totalComments, int totalUpVote, int totalDownVote, int totalShare, int totalView, Date createdAt,
			Date updatedAt, List<PostReport> postReports, User author, List<Reaction> reactions, List<Comment> comments,
			List<BookMark> bookMarks, List<Topic> topics) {
		super();
		this.postId = postId;
		this.title = title;
		this.description = description;
		this.content = content;
		this.url = url;
		this.state = state;
		this.totalComments = totalComments;
		this.totalUpVote = totalUpVote;
		this.totalDownVote = totalDownVote;
		this.totalShare = totalShare;
		this.totalView = totalView;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.postReports = postReports;
		this.author = author;
		this.reactions = reactions;
		this.comments = comments;
		this.bookMarks = bookMarks;
		this.topics = topics;
	}

	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(postId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return postId == other.postId;
	}

}

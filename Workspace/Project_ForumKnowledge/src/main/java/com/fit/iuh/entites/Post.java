package com.fit.iuh.entites;

import com.fit.iuh.enums.PostState;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="posts")
@EntityListeners(AuditingEntityListener.class)  // Kích hoạt Auditing cho entity này
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="post_id")
	private int postId;

	@Column(name="title", nullable = false, unique = false, columnDefinition = "")
	private String title;

	@Column(name="description", nullable = false, unique = false, columnDefinition = "")
	private String description;

	@Column(name="content", nullable = false, unique = false, columnDefinition = "")
	private String content;

	@Column(name="url", nullable = false, unique = false, columnDefinition = "")
	private String url;

	@Enumerated(EnumType.STRING)
	@Column(name="state", nullable = false, unique = false, columnDefinition = "")
	private PostState state;

	@Column(name="total_comments", nullable = false, unique = false, columnDefinition = "")
	private int totalComments;

	@Column(name="total_up_vote", nullable = false, unique = false, columnDefinition = "")
	private int totalUpVote;

	@Column(name="total_down_vote", nullable = false, unique = false, columnDefinition = "")
	private int totalDownVote;

	@Column(name="total_share", nullable = false, unique = false, columnDefinition = "")
	private int totalShare;

	@Column(name="total_view", nullable = false, unique = false, columnDefinition = "")
	private int totalView;

	@Column(name="created_at", nullable = false, unique = false, columnDefinition = "")
	@CreatedDate
	private Date createdAt;

	@Column(name="updated_at", nullable = false, unique = false, columnDefinition = "")
	@LastModifiedDate
	private Date updatedAt;

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
	private List<PostReport> postReports;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="author_id")
	private User author;

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
	private List<Reaction> reactions;

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
	private List<Comment> comments;

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
	private List<BookMark> bookMarks;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tag_id", referencedColumnName = "tag_id")
	private Topic topic;

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

	public PostState getState() {
		return state;
	}

	public void setState(PostState state) {
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

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topics) {
		this.topic = topics;
	}

	public Post(int postId, String title, String description, String content, String url, PostState state,
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

	public Post(int postId, String title, String description, String content, String url, PostState state,
				int totalComments, int totalUpVote, int totalDownVote, int totalShare, int totalView, Date createdAt,
				Date updatedAt, List<PostReport> postReports, User author, List<Reaction> reactions, List<Comment> comments,
				List<BookMark> bookMarks, Topic topic) {
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
		this.topic = topic;
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

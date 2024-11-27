package com.fit.iuh.entites;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="topics")
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tag_id")
	private int tagId;

	@Column(name="name", nullable = false, unique = false, columnDefinition = "")
	private String name;

	@Column(name="hashtag", nullable = false, unique = false, columnDefinition = "")
	private String hashtag;

	@Column(name="created_at", nullable = false, unique = false, columnDefinition = "")
	private Date createdAt;

	@Column(name="updated_at", nullable = false, unique = false, columnDefinition = "")
	private Date updatedAt;

	@OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
	private List<Post> posts;

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
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

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Topic(int tagId, String name, String hashtag, Date createdAt, Date updatedAt) {
		super();
		this.tagId = tagId;
		this.name = name;
		this.hashtag = hashtag;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Topic(int tagId, String name, String hashtag, Date createdAt, Date updatedAt, List<Post> posts) {
		super();
		this.tagId = tagId;
		this.name = name;
		this.hashtag = hashtag;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.posts = posts;
	}

	public Topic(int tagId, String hashtag, String name) {
		this.tagId = tagId;
		this.hashtag = hashtag;
		this.name = name;
	}

	public Topic() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		return Objects.hash(tagId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		return tagId == other.tagId;
	}

	@Override
	public String toString() {
		return "Topic{" +
				"tagId=" + tagId +
				", name='" + name + '\'' +
				", hashtag='" + hashtag + '\'' +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				", posts=" + posts +
				'}';
	}
}

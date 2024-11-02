package com.fit.iuh.entites;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fit.iuh.enums.UserAccountState;

public class User {
	private int userId;

	private String name;

	private int role;

	private UserAccountState accountState;

	private String email;

	private String title;

	private String passwordHash;

	private String profilePicture;

	private String bio;

	private Date createdAt;

	private Date updatedAt;

	private List<PostReport> reporters;

	private List<PostReport> inspectors;

	private List<BanList> banList;

	private List<BookMark> bookMarks;

	private List<Comment> comments;

	private List<Reaction> reactions;

	private List<Post> posts;

	private List<Following> followers;

	private List<Following> followings;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public UserAccountState getAccountState() {
		return accountState;
	}

	public void setAccountState(UserAccountState accountState) {
		this.accountState = accountState;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
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

	public List<PostReport> getReporters() {
		return reporters;
	}

	public void setReporters(List<PostReport> reporters) {
		this.reporters = reporters;
	}

	public List<PostReport> getInspectors() {
		return inspectors;
	}

	public void setInspectors(List<PostReport> inspectors) {
		this.inspectors = inspectors;
	}

	public List<BanList> getBanList() {
		return banList;
	}

	public void setBanList(List<BanList> banList) {
		this.banList = banList;
	}

	public List<BookMark> getBookMarks() {
		return bookMarks;
	}

	public void setBookMarks(List<BookMark> bookMarks) {
		this.bookMarks = bookMarks;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(List<Reaction> reactions) {
		this.reactions = reactions;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Following> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Following> followers) {
		this.followers = followers;
	}

	public List<Following> getFollowings() {
		return followings;
	}

	public void setFollowings(List<Following> followings) {
		this.followings = followings;
	}

	public User(int userId, String name, int role, UserAccountState accountState, String email, String title,
			String passwordHash, String profilePicture, String bio, Date createdAt, Date updatedAt) {
		super();
		this.userId = userId;
		this.name = name;
		this.role = role;
		this.accountState = accountState;
		this.email = email;
		this.title = title;
		this.passwordHash = passwordHash;
		this.profilePicture = profilePicture;
		this.bio = bio;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public User(int userId, String name, int role, UserAccountState accountState, String email, String title,
			String passwordHash, String profilePicture, String bio, Date createdAt, Date updatedAt,
			List<PostReport> reporters, List<PostReport> inspectors, List<BanList> banList, List<BookMark> bookMarks,
			List<Comment> comments, List<Reaction> reactions, List<Post> posts, List<Following> followers,
			List<Following> followings) {
		super();
		this.userId = userId;
		this.name = name;
		this.role = role;
		this.accountState = accountState;
		this.email = email;
		this.title = title;
		this.passwordHash = passwordHash;
		this.profilePicture = profilePicture;
		this.bio = bio;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.reporters = reporters;
		this.inspectors = inspectors;
		this.banList = banList;
		this.bookMarks = bookMarks;
		this.comments = comments;
		this.reactions = reactions;
		this.posts = posts;
		this.followers = followers;
		this.followings = followings;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return userId == other.userId;
	}

}

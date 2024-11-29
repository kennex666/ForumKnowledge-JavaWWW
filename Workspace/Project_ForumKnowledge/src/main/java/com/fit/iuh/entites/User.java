package com.fit.iuh.entites;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fit.iuh.enums.UserAccountState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)  // Kích hoạt Auditing cho entity này
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private int userId;

	@Column(name="name", nullable = false, unique = false, columnDefinition = "")
	@NotBlank(message = "Tên đăng nhập không được để trống")
	private String name;

	@Column(name="role", nullable = false, unique = false, columnDefinition = "")
	private int role;

	// Method sẽ được gọi trước khi đối tượng được lưu vào cơ sở dữ liệu
	@PrePersist
	public void setDefaultValues() {
		if (this.accountState == null) {
			this.accountState = UserAccountState.ACTIVE; // Giá trị mặc định
		}
	}

	@Enumerated(EnumType.STRING)
	@Column(name="account_state", nullable = false , unique = false, columnDefinition = "")
		private UserAccountState accountState;

	@Column(name="email", nullable = false, unique = true, columnDefinition = "")
	@Email(message = "Định dạng email không hợp lệ. VD: me@dtbao.io.vn")
	@NotBlank(message = "Email không được để trống")
	private String email;

	@Column(name="title", nullable = true, unique = false, columnDefinition = "")
	private String title;

	@JsonIgnore
	@Column(name="password_hash", nullable = false, unique = false, columnDefinition = "")
	@Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
	private String passwordHash;

	@Column(name="profile_picture", nullable = true, unique = false, columnDefinition = "")
	private String profilePicture;

	@Column(name="bio", nullable = true, unique = false, columnDefinition = "")
	private String bio;

	@Column(name="created_at", nullable = false, unique = false, columnDefinition = "")
	@CreatedDate
	private Date createdAt;

	@Column(name="updated_at", nullable = false, unique = false, columnDefinition = "")
	@LastModifiedDate
	private Date updatedAt;

	@JsonIgnore
	@OneToMany(mappedBy = "reporter", fetch = FetchType.LAZY)
	private List<PostReport> reporters;

	@JsonIgnore
	@OneToMany(mappedBy = "inspector", fetch = FetchType.LAZY)
	private List<PostReport> inspectors;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<BanList> banList;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<BookMark> bookMarks;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Comment> comments;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Reaction> reactions;

	@JsonIgnore
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	private List<Post> posts;

	@JsonIgnore
	@OneToMany(mappedBy = "followed", fetch = FetchType.LAZY)
	private List<Following> followers;

	@JsonIgnore
	@OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
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

	public User(int userId) {
		super();
		this.userId = userId;
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

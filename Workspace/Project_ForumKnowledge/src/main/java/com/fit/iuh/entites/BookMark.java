	package com.fit.iuh.entites;
	
	import jakarta.persistence.*;
	import org.springframework.data.jpa.domain.support.AuditingEntityListener;
	
	import java.util.Date;
	import java.util.Objects;
	
	@Entity
	@Table(name="bookmarks")
	@EntityListeners(AuditingEntityListener.class)  // Kích hoạt Auditing cho entity này
	public class BookMark {
	
		@Id
		@Column(name="id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
	
		@Column(name="created_at", nullable = false, unique = false, columnDefinition = "")
		private Date createdAt;
	
		@Column(name="updated_at", nullable = false, unique = false, columnDefinition = "")
	
		private Date updatedAt;
	
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="user_id", referencedColumnName = "user_id")
		private User user;
	
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="post_id", referencedColumnName = "post_id")
		private Post post;
	
		public int getId() {
			return id;
		}
	
		public void setId(int id) {
			this.id = id;
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
	
		public void setUser(User user) {
			this.user = user;
		}
	
		public Post getPost() {
			return post;
		}
	
		public void setPost(Post post) {
			this.post = post;
		}
	
		public BookMark(int id, Date createdAt, Date updatedAt, User user, Post post) {
			super();
			this.id = id;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
			this.user = user;
			this.post = post;
		}
	
		public BookMark(int id, Date createdAt, Date updatedAt) {
			super();
			this.id = id;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
		}
	
		public BookMark() {
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
			BookMark other = (BookMark) obj;
			return id == other.id;
		}
	
	}

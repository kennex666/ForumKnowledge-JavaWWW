package com.fit.iuh.entites;

import java.util.Date;
import java.util.Objects;

import com.fit.iuh.enums.BanListState;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="ban_lists")
@EntityListeners(AuditingEntityListener.class)  // Kích hoạt Auditing cho entity này
public class BanList {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Enumerated(EnumType.STRING)
	@Column(name="state", nullable = false, unique = false, columnDefinition = "")
	private BanListState state;

	@Column(name="reason", nullable = false, unique = false, columnDefinition = "")
	private String reason;

	@Column(name="time_start", nullable = false, unique = false, columnDefinition = "")
	private Date timeStart;

	@Column(name="time_end", nullable = false, unique = false, columnDefinition = "")
	private Date timeEnd;

	@Column(name="created_at", nullable = false, unique = false, columnDefinition = "")
	@CreatedDate
	private Date createdAt;

	@Column(name="updated_at", nullable = false, unique = false, columnDefinition = "")
	@LastModifiedDate
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", referencedColumnName = "user_id")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BanListState getState() {
		return state;
	}

	public void setState(BanListState state) {
		this.state = state;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
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

	public BanList(int id, BanListState state, String reason, Date timeStart, Date timeEnd, Date createdAt,
			Date updatedAt) {
		super();
		this.id = id;
		this.state = state;
		this.reason = reason;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public BanList(int id, BanListState state, String reason, Date timeStart, Date timeEnd, Date createdAt,
			Date updatedAt, User user) {
		super();
		this.id = id;
		this.state = state;
		this.reason = reason;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.user = user;
	}

	public BanList() {
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
		BanList other = (BanList) obj;
		return id == other.id;
	}

}

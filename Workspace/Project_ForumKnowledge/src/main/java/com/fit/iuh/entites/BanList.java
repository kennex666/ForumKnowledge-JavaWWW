package com.fit.iuh.entites;

import java.util.Date;
import java.util.Objects;

import com.fit.iuh.enums.BanListState;
import jakarta.persistence.*;

@Entity
@Table(name="ban_list")
public class BanList {

	@Id
	@Column(name="id", nullable = false, unique = true, columnDefinition = "")
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

	@Column(name="create_at", nullable = false, unique = false, columnDefinition = "")
	private Date createAt;

	@Column(name="update_at", nullable = false, unique = false, columnDefinition = "")
	private Date updateAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable = false)
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BanList(int id, BanListState state, String reason, Date timeStart, Date timeEnd, Date createAt,
			Date updateAt) {
		super();
		this.id = id;
		this.state = state;
		this.reason = reason;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	public BanList(int id, BanListState state, String reason, Date timeStart, Date timeEnd, Date createAt,
			Date updateAt, User user) {
		super();
		this.id = id;
		this.state = state;
		this.reason = reason;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.createAt = createAt;
		this.updateAt = updateAt;
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

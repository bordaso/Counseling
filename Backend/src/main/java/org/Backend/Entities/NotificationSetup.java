package org.Backend.Entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Table
@Scope("prototype")
@Component
public class NotificationSetup implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	private Long id;
	
	//@Column
	@MapsId
	@OneToOne(mappedBy = "notificationId", fetch = FetchType.EAGER)
	private Bookings boookingNotifId;
	
	@Column
	private long recurringTime;
	
	@Column
	private String content;
	
	@Column
	private boolean email;
	
	@Column
	private boolean sms;
	
	@Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Bookings getBoookingNotifId() {
		return boookingNotifId;
	}

	public void setBoookingNotifId(Bookings boookingNotifId) {
		this.boookingNotifId = boookingNotifId;
	}

	public long getRecurringTime() {
		return recurringTime;
	}

	public void setRecurringTime(long recurringTime) {
		this.recurringTime = recurringTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isEmail() {
		return email;
	}

	public void setEmail(boolean email) {
		this.email = email;
	}

	public boolean isSms() {
		return sms;
	}

	public void setSms(boolean sms) {
		this.sms = sms;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boookingNotifId == null) ? 0 : boookingNotifId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotificationSetup other = (NotificationSetup) obj;
		if (boookingNotifId == null) {
			if (other.boookingNotifId != null)
				return false;
		} else if (!boookingNotifId.equals(other.boookingNotifId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NotificationSetup [id=" + id + ", boookingNotifId=" + boookingNotifId + ", recurringTime="
				+ recurringTime + ", content=" + content + ", email=" + email + ", sms=" + sms + "]";
	}
	
	
	

}

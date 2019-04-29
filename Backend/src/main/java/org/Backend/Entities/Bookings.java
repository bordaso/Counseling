package org.Backend.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Table
@Scope("prototype")
@Component
public class Bookings implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(updatable = false, nullable = false)
	private String title;
	
	@Column
	@OneToMany(mappedBy="booking", fetch = FetchType.EAGER)
	private List<BookingDetails> bookingDetails;

	@Lob
	@Column(columnDefinition = "BINARY(200000)") /* 200k byte, a small pdf is around 80k */
	private byte[] report;
	 
	@Column(columnDefinition = "SMALLDATETIME", updatable = false, nullable = false)
	private LocalDateTime start;
	
	@Column(columnDefinition = "SMALLDATETIME", updatable = false, nullable = false)
	private LocalDateTime end;

	@Column(updatable = false, nullable = false)
	private String room;

	//@Column mappedBy = "boookingNotifId", mappedBy = "notificationId"
	@OneToOne( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "id")
	private NotificationSetup notificationId;

	@Column
	private boolean archived;

	@Version
	@Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
	private long version = 0L;
	
	
	public Bookings() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte[] getReport() {
		return report;
	}

	public void setReport(byte[] report) {
		this.report = report;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start.truncatedTo(ChronoUnit.MINUTES);
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end.truncatedTo(ChronoUnit.MINUTES);
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public NotificationSetup getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(NotificationSetup notificationId) {
		this.notificationId = notificationId;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public List<BookingDetails> getBookingDetails() {
		return bookingDetails;
	}

//	public void setBookingDetails(List<BookingDetails> bookingDetails) {
//		this.bookingDetails = bookingDetails;
//	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Bookings other = (Bookings) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bookings [id=" + id + " title=" + title + ", start=" + start + ", end=" + end + ", room=" + room + "]";
	}

}

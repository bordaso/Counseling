package org.Backend.Entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.Backend.Enums.UserType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Table
@Scope("prototype")
@Component
public class Patient extends User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(updatable = false, nullable = false)
	private Long medicalId;
	
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="employee_id")
	private Employee counselor;	
	
	@Column
	@OneToMany(mappedBy="patient")
	private Set<BookingDetails> bookingDetails;
	
	@Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;
	
	public Patient() {
		super();
		this.type=UserType.PATIENT;
	}

	public Long getMedicalId() {
		return medicalId;
	}

	public void setMedicalId(Long medicalId) {
		this.medicalId = medicalId;
	}

	public Employee getCounselor() {
		return counselor;
	}

	public void setCounselor(Employee counselor) {
		this.counselor = counselor;
	}

	public Set<BookingDetails> getBookingDetails() {
		return bookingDetails;
	}

	public void setBookingDetails(Set<BookingDetails> bookingDetails) {
		this.bookingDetails = bookingDetails;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((medicalId == null) ? 0 : medicalId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Patient other = (Patient) obj;
		if (medicalId == null) {
			if (other.medicalId != null)
				return false;
		} else if (!medicalId.equals(other.medicalId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", name=" + name + ", email=" + email + ", counselor=" + counselor + "]";
	}
	
	
}

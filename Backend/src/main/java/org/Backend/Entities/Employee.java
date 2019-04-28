package org.Backend.Entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Employee extends User implements Serializable {
	
//	 @PreUpdate
//	  private void preUpdate() {
//	    throw new UnsupportedOperationException();
//	  }
	
	private static final long serialVersionUID = 1L; 
	
	@Column(updatable = false, nullable = false)
	private String personalId;
	
	@Column(columnDefinition = "BINARY(1000)")
	private Employee reportsTo;

	@Column
	@OneToMany(fetch=FetchType.EAGER, mappedBy="counselor")
	private List<Patient> patientList;
	
	@Column
	@OneToMany(fetch=FetchType.EAGER, mappedBy="employee")
	private Set<BookingDetails> bookingDetails;

	@Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;	

	public Employee() {
		super();
		this.type=UserType.EMPLOYEE;
	}
	
	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	public Employee getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(Employee reportsTo) {
		this.reportsTo = reportsTo;
	}

	public List<Patient> getPatientList() {
		return patientList;
	}

	public void setPatientList(List<Patient> patientList) {
		this.patientList = patientList;
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((personalId == null) ? 0 : personalId.hashCode());
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
		Employee other = (Employee) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (personalId == null) {
			if (other.personalId != null)
				return false;
		} else if (!personalId.equals(other.personalId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", email=" + email + "]";
	}
	

}

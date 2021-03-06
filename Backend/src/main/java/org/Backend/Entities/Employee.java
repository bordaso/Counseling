package org.Backend.Entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	
	@ManyToOne(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
    @JoinColumn(name="manager_id")
	private Employee reportsTo;
	
	@OneToMany(mappedBy="reportsTo")
    private Set<Employee> subordinates = new HashSet<Employee>();


	@Column
	@OneToMany(fetch=FetchType.EAGER, mappedBy="counselor")
	@Fetch (FetchMode.SELECT) 
	@JsonManagedReference(value="patient-list")
	private List<Patient> patientList;
	
	@Column
	@OneToMany(fetch=FetchType.EAGER, mappedBy="employee")
	@Fetch (FetchMode.SELECT) 
	@JsonManagedReference(value="employee-bookings")
	private Set<BookingDetails> bookingDetails;


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

	public Set<Employee> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(Set<Employee> subordinates) {
		this.subordinates = subordinates;
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

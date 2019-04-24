package org.Backend.Entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.Backend.Enums.BookingResponse;
import org.springframework.stereotype.Component;

@Entity
@Table
@Component
public class BookingDetails implements Serializable {
	
	private static final long serialVersionUID = 1L; 
	
	//@Autowired
//	@EmbeddedId
//	private BookingDetailsId id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Long id;
	
	//@MapsId("bookingId")
	//@Column(updatable = false, nullable = false)
	@ManyToOne 
	private Bookings booking;
	
	//@MapsId("employeeId")
	//@Column
	@ManyToOne 
	private Employee employee;
	

	//@MapsId("patientId")
	//@Column
	@ManyToOne 
	private Patient patient;
	
	@Column
	@Enumerated(EnumType.STRING)
	private BookingResponse response=BookingResponse.NO_RESPONSE;
	
	@Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;	
	
	
	public BookingDetails() {
		super();
		//this.id=new BookingDetailsId(); //---------------->>>>>>> Try Spring to do it
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Bookings getBooking() {
		return booking;
	}


	public void setBooking(Bookings booking) {
		this.booking = booking;
	}


	public Employee getEmployee() {
		return employee;
	}


	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	public Patient getPatient() {
		return patient;
	}


	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public BookingResponse getResponse() {
		return response;
	}

	public void setResponse(BookingResponse response) {
		this.response = response;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


//	public BookingDetailsId getId() {
//		return id;
//	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((booking == null) ? 0 : booking.hashCode());
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
		BookingDetails other = (BookingDetails) obj;
		if (booking == null) {
			if (other.booking != null)
				return false;
		} else if (!booking.equals(other.booking))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "BookingDetails [id=" + id + ", booking=" + booking + ", employee=" + employee + ", patient=" + patient
				+ "]";
	}
	
}

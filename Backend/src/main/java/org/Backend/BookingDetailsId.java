package org.Backend;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.springframework.stereotype.Component;

//@Component
//@Embeddable
public class BookingDetailsId implements Serializable {
	
	private static final long serialVersionUID = 1L; 
	
	private long employeeId;
	
	private long patientId;
	
	private long bookingId;
	
	public BookingDetailsId() {
		super();
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

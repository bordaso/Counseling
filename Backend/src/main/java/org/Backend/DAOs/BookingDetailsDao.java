package org.Backend.DAOs;

import java.util.List;

import org.Backend.Entities.BookingDetails;
import org.Backend.Entities.Bookings;
import org.Backend.Entities.Employee;
import org.Backend.Entities.Patient;
import org.Backend.Enums.BookingResponse;

public interface BookingDetailsDao {

	void saveBookingDetails(BookingDetails input);

	void updateBookingDetails(BookingDetails toBeUpdated);
	
	void deleteBookingDetails(Long id);

	BookingDetails selectBookingDetailsById(Long id);

	List<BookingDetails> selectBookingDetailsByBooking(Bookings booking);
	
	List<BookingDetails> selectBookingDetailsByEmployee(String personalId);
	
	List<BookingDetails> selectBookingDetailsByPatient(Long medicalId);
	
	List<BookingDetails> selectAllBookingDetails();

	void updateBookingDetailsBookings(Long id, Bookings booking);
	
	void updateBookingDetailsEmployee(Long id, Employee employee);
	
	void updateBookingDetailsPatient(Long id, Patient patient);
	
	void updateBookingDetailsResponse(Long id, BookingResponse newValue);
	
	
	void clearBookingDetails();

	void setMyProxy(BookingDetailsDao proxy);

}

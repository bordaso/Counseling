package org.Backend.DAOs;

import java.time.LocalDateTime;
import java.util.List;

import org.Backend.Entities.Bookings;

public interface BookingsDao {

	void saveBooking(Bookings input);

	void updateBooking(Bookings toBeUpdated);
	
	void deleteBooking(Long id);

	Bookings selectBookingById(Long id);

	List<Bookings> selectAllBooking();

	List<Bookings> selectBookingByTitle(String inputTitle);

	void updateBookingTitle(Long id, String newTitle);
	
	void updateBookingReport(Long id, byte[] newReport);
	
	void updateBookingStart(Long id, LocalDateTime newStart);
	
	void updateBookingEnd(Long id, LocalDateTime newEnd);

	void updateBookingRoom(Long id, String newRoom);

	void updateBookingArchived(Long id, boolean newValue);

	
	
	void clearBookings();

	void setMyProxy(BookingsDao proxy);

}

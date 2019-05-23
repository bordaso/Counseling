package org.Backend.Services;

import org.Backend.DAOs.BookingDetailsDao;
import org.Backend.DAOs.BookingsDao;
import org.Backend.Entities.BookingDetails;
import org.Backend.Enums.BookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonService {
	
	@Autowired
	private BookingDetailsDao bookdDao;
	
	@Autowired
	private BookingsDao bookDao;	
	
	public boolean updateBookingResponse(Long id, BookingResponse newValue) {
		
		boolean success=false;
		
		bookdDao.updateBookingDetailsResponse (id, newValue);
		
		BookingDetails bookingDetails = bookdDao.selectBookingDetailsById(id);
		
		
		
		if(bookingDetails != null && bookingDetails.getResponse().equals(newValue) ) {
			success=true;
		}
	
		return success;
	}
	
	
	
	
}

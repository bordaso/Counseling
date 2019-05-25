package org.Backend.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.Backend.DAOs.BookingDetailsDao;
import org.Backend.DAOs.BookingsDao;
import org.Backend.DAOs.EmployeeDao;
import org.Backend.Entities.BookingDetails;
import org.Backend.Entities.Bookings;
import org.Backend.Entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeService {
	
	@Autowired
	private EmployeeDao empDao;

	@Autowired
	private BookingDetailsDao bookdDao;
	
	@Autowired
	private BookingsDao bookDao;

	public Employee employeeFinderByUsername(String username) {

		return empDao.selectEmployeetByUsername(username).get(0);
	}
	
	public Map<Bookings, BookingDetails> employeeFindAllUpcomingBookings(String username) {
		
		Map<Bookings, BookingDetails> returnMap = new HashMap<Bookings, BookingDetails>();
		
		Employee emp = empDao.selectEmployeetByUsername(username).get(0);
		
		List<BookingDetails> bookdList = bookdDao.selectBookingDetailsByEmployee(emp);
		
		for(BookingDetails bd : bookdList) {
			returnMap.put(bd.getBooking(), bd);
		}
		
		return returnMap;			
	}
}

package org.Backend.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.Backend.DAOs.BookingDetailsDao;
import org.Backend.DAOs.BookingsDao;
import org.Backend.DAOs.PatientDao;
import org.Backend.Entities.BookingDetails;
import org.Backend.Entities.Bookings;
import org.Backend.Entities.Employee;
import org.Backend.Entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientService {

	@Autowired
	private PatientDao patDao;
	
	@Autowired
	private BookingDetailsDao bookdDao;
	
	@Autowired
	private BookingsDao bookDao;

	public Patient patientFinderByUsername(String username) {
		
		return patDao.selectPatientByUsername(username).get(0);		
	}
	
	public Map<Bookings, BookingDetails> patientFindAllUpcomingBookings(String username) {		
		
		Map<Bookings, BookingDetails> returnMap = new HashMap<>();
		
		Patient pat = patDao.selectPatientByUsername(username).get(0);
		
		List<BookingDetails> bookdList = bookdDao.selectBookingDetailsByPatient(pat);
		
		for(BookingDetails bd : bookdList) {
			returnMap.put(bd.getBooking(), bd);
		}
		
		return returnMap;		
	}
	
	
	
	
}

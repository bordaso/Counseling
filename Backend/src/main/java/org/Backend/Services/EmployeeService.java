package org.Backend.Services;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.Backend.DAOs.BookingDetailsDao;
import org.Backend.DAOs.BookingsDao;
import org.Backend.DAOs.EmployeeDao;
import org.Backend.DAOs.PatientDao;
import org.Backend.Entities.BookingDetails;
import org.Backend.Entities.Bookings;
import org.Backend.Entities.Employee;
import org.Backend.Entities.Patient;
import org.Backend.Entities.User;
import org.Backend.Enums.BookingResponse;
import org.Backend.Enums.UserType;
import org.Backend.Utilities.BeanUtil;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class EmployeeService {
	
	@Autowired
	private EmployeeDao empDao;

	@Autowired
	private BookingDetailsDao bookdDao;
	
	@Autowired
	private BookingsDao bookDao;
	
	@Autowired
	private PatientDao patDao;	
	

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
	
	
	
	public List<User> selectMyUsers() {
		
		List<Employee> allEmps = empDao.selectAllEmployee();		
		List<User> returnVals = new ArrayList<User>();
		
		String pudUser = employeeValidationUsernameReturn();
		    
		Employee myself = empDao.selectEmployeetByUsername(pudUser).get(0);		
		allEmps.remove(allEmps.indexOf(myself)); 		    
		List<Patient> allMyPat =  myself.getPatientList();
		    
		returnVals.addAll(allEmps);
		returnVals.addAll(allMyPat);
		    
		return returnVals;		 
	}
	
	
public void createBooking(List<User> invitees, Bookings newBooking) {
	
	String pudUser = employeeValidationUsernameReturn();
	    
	Employee myself = empDao.selectEmployeetByUsername(pudUser).get(0);	

	
	BookingDetails newBookingDetails = BeanUtil.getBean(BookingDetails.class);
    newBookingDetails.setBooking(newBooking);
	newBookingDetails.setEmployee(myself);
	newBookingDetails.setResponse(BookingResponse.ACCEPTED);
	
	bookDao.saveBooking(newBooking);
	bookdDao.saveBookingDetails(newBookingDetails);
	
	for (User u : invitees) {
		
		newBookingDetails = BeanUtil.getBean(BookingDetails.class);
	    newBookingDetails.setBooking(newBooking);
		
		if(u.getType().equals(UserType.EMPLOYEE) || u.getType().equals(UserType.ADMIN)) {
			Employee emp = empDao.selectEmployeetByUsername(u.getUsername()).get(0);
			newBookingDetails.setEmployee(emp);
			bookdDao.saveBookingDetails(newBookingDetails);
			continue;
		}
		Patient pat = patDao.selectPatientByUsername(u.getUsername()).get(0);
		newBookingDetails.setPatient(pat);
		bookdDao.saveBookingDetails(newBookingDetails);
	}
	
		
	}


public Map<Bookings, BookingDetails> createHighlightEventsReturnBookings(ScheduleModel sm) {
	
	String pudUser = employeeValidationUsernameReturn();
	    
	Employee myself = empDao.selectEmployeetByUsername(pudUser).get(0);
		
	
	
	Map<Bookings, BookingDetails> bookingsMap = employeeFindAllUpcomingBookings(pudUser);	
	
	for(Map.Entry<Bookings, BookingDetails> entry : bookingsMap.entrySet()) {
	
		if (entry.getValue().getResponse().equals(BookingResponse.ACCEPTED)) {
			
			sm.addEvent(new DefaultScheduleEvent(entry.getKey().getTitle(),
					Date.from(entry.getKey().getStart().atZone(ZoneId.systemDefault()).toInstant()), 
					Date.from(entry.getKey().getEnd().atZone(ZoneId.systemDefault()).toInstant())
					));
		}
	}
	
    return bookingsMap;

}

public String employeeValidationUsernameReturn() {	
	
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	Object princ = auth.getPrincipal();
	UserDetails	princUD;
	String pudUser="";	
	
	if(princ instanceof UserDetails && (auth.getAuthorities().iterator().next().getAuthority().equals("ROLE_EMPLOYEE")
			|| auth.getAuthorities().iterator().next().getAuthority().equals("ROLE_ADMIN"))) {
		
		princUD = (UserDetails)princ;			
	    pudUser = princUD.getUsername();	
		}	
	
	
	return pudUser;
}

	
}

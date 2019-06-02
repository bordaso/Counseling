package org.Backend;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

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
import org.Backend.Services.CommonService;
import org.Backend.Services.EmployeeService;
import org.Backend.Services.PatientService;
import org.Backend.Utilities.ReportCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.itextpdf.text.DocumentException;

public class DashboardBean extends SpringBeanAutowiringSupport implements Serializable {

	private Map<Bookings, BookingDetails> bookingsMap = new HashMap<Bookings, BookingDetails>();
	
	private Set<Map.Entry<User, Bookings>> bookingsSetAdditional = new HashSet<Map.Entry<User, Bookings>>();
	
	private Entry<Bookings, BookingDetails> selectedBooking; 	

	private List<Employee> employees;

	private List<Patient> patient;

	private List<Employee> managerSelected = new ArrayList<Employee>();

	private List<Employee> counselorSelected = new ArrayList<Employee>();

	private List<Patient> patientSelected = new ArrayList<Patient>();

	private List<UserType> types = new ArrayList<UserType>();

	private Map<String, String> requestParams;	
	
	private String reportSummary ="";
	
	@Autowired
	private ReportCreator reportCreator;

	@Autowired
	private EmployeeService es;

	@Autowired
	private EmployeeDao ed;

	@Autowired
	private CommonService cs;

	@Autowired
	private PatientService ps;

	@Autowired
	private PatientDao pd;
	
	@Autowired
	private BookingsDao bd;

	@PostConstruct
	public void init() {
		
		bookingsMap = getPastBookings(es.employeeFindAllUpcomingBookings("tester"));
		
		
		
		employees = ed.selectAllEmployee();
		patient = pd.selectAllPatient();
		types = Arrays.asList(UserType.values());
	}

	private Map<Bookings, BookingDetails> getPastBookings(
			Map<Bookings, BookingDetails> employeeFindAllUpcomingBookings) {
		
		Map<Bookings, BookingDetails> pastBookings = employeeFindAllUpcomingBookings.entrySet().stream()
				.filter(x -> x.getKey().getStart().toLocalDate().isBefore(LocalDate.now()) && x.getValue().getResponse().equals(BookingResponse.ACCEPTED) )
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));		
		
		return pastBookings;
	}
	
//	 bkngs1.setReport(reportCreator.retrieveAsByte(bkngs1.getBookingDetails(), emp, "We had a great conversation, and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum."));		 
//	 bkngsDao.updateBooking(bkngs1);
//	 
//	 final Long bkngsId3=bkngs1.getId();		 
//	 assertEquals(true, reportCreator.generateFileFromByte(bkngsDao.selectBookingById(bkngsId3).getReport()));
	
	
	public void openSubmitDialog(Map.Entry<Bookings, BookingDetails> selectedEntry) {
		selectedBooking =  new AbstractMap.SimpleEntry<Bookings, BookingDetails>(selectedEntry.getKey(), selectedEntry.getValue());
	}
	
	public void submitReport() throws DocumentException {

		selectedBooking.getKey().setReport(reportCreator.retrieveAsByte(selectedBooking.getKey().getBookingDetails(), selectedBooking.getValue().getEmployee(), reportSummary));
		selectedBooking.getKey().setArchived(true);
		bd.updateBooking(selectedBooking.getKey());
		bookingsMap = getPastBookings(es.employeeFindAllUpcomingBookings("tester"));
	}
	
	public void viewReport(Map.Entry<Bookings, BookingDetails> selectedEntry) {

		System.out.println(selectedEntry);
	}
	

	public String getReportSummary() {
		return reportSummary;
	}

	public void setReportSummary(String reportSummary) {
		this.reportSummary = reportSummary;
	}

	public Map<Bookings, BookingDetails> getBookingsMap() {
		return bookingsMap;
	}

	public void setBookingsMap(Map<Bookings, BookingDetails> bookingsMap) {
		this.bookingsMap = bookingsMap;
	}

	
	private void clearUp() {		
	}

}
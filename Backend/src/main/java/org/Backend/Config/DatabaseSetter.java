package org.Backend.Config;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.Backend.DAOs.BookingDetailsDao;
import org.Backend.DAOs.BookingsDao;
import org.Backend.DAOs.EmployeeDao;
import org.Backend.DAOs.NotificationSetupDao;
import org.Backend.DAOs.PatientDao;
import org.Backend.Entities.BookingDetails;
import org.Backend.Entities.Bookings;
import org.Backend.Entities.Employee;
import org.Backend.Entities.NotificationSetup;
import org.Backend.Entities.Patient;
import org.Backend.Enums.BookingResponse;
import org.Backend.Utilities.BeanUtil;
import org.Backend.Utilities.ReportCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSetter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmployeeDao empDao;

	@Autowired
	private PatientDao patDao;

	@Autowired
	private Employee empBoss;

	@Autowired
	private Employee emp;

	@Autowired
	private Patient pat1;

	@Autowired
	private Patient pat2;

	@Autowired
	private Patient pat3;

	@Autowired
	private Bookings bkngs1;

	@Autowired
	private Bookings bkngs2;
	
	@Autowired
	private Bookings bkngs3;

	@Autowired
	private ReportCreator reportCreator;

	@Autowired
	private BookingDetails bkngs1_bkngsDtls1;

	@Autowired
	private BookingDetails bkngs1_bkngsDtls2;

	@Autowired
	private BookingDetails bkngs1_bkngsDtls3;

	@Autowired
	private BookingDetails bkngs2_bkngsDtls4;

	@Autowired
	private BookingDetails bkngs2_bkngsDtls5;
	
	@Autowired
	private BookingDetails bkngs2_bkngsDtls6;
	
	@Autowired
	private BookingDetails bkngs3_bkngsDtls7;

	@Autowired
	private NotificationSetup bkngs1_ntfsnStp1;

	@Autowired
	private NotificationSetup bkngs2_ntfsnStp2;

	@Autowired
	private BookingsDao bkngsDao;

	@Autowired
	private BookingDetailsDao bkngsDtlsDao;

	@Autowired
	private NotificationSetupDao ntfsnStpDao;

	private List<BookingDetails> listOfBookingDetails;

	private void setupBkngs1() {
		bkngs1.setTitle("booking1");
		bkngs1.setStart(LocalDateTime.of(2019, Month.APRIL, 11, 14, 00));
		bkngs1.setEnd(LocalDateTime.of(2019, Month.APRIL, 11, 15, 00));
		bkngs1.setRoom("room1");
		bkngs1.setArchived(false);

		// nem állítunk be booking oldalról listát!
		// bkngs1.setBookingDetails(listOfBookingDetails);
	}

	private void setupBkngs2() {
		bkngs2.setTitle("booking2");
		bkngs2.setStart(LocalDateTime.of(2019, Month.APRIL, 16, 12, 00));
		bkngs2.setEnd(LocalDateTime.of(2019, Month.APRIL, 16, 13, 00));
		bkngs2.setRoom("room2");
		bkngs2.setArchived(true);
	}

	private void setupBkngs3() {
		bkngs3.setTitle("booking33");
		bkngs3.setStart(LocalDateTime.of(2019, Month.APRIL, 16, 12, 00));
		bkngs3.setEnd(LocalDateTime.of(2019, Month.APRIL, 16, 13, 00));
		bkngs3.setRoom("room33");
		bkngs3.setArchived(false);
	}
	
	private void setupListOfBookingDetails() {
		listOfBookingDetails = new ArrayList<BookingDetails>();
		listOfBookingDetails.add(bkngs1_bkngsDtls1);
		listOfBookingDetails.add(bkngs1_bkngsDtls2);
		listOfBookingDetails.add(bkngs1_bkngsDtls3);
	}

	private void setupBkngs1_BkngsDtls1() {
		bkngs1_bkngsDtls1.setBooking(bkngs1);
		bkngs1_bkngsDtls1.setEmployee(empBoss);
		bkngs1_bkngsDtls1.setPatient(null);
		bkngs1_bkngsDtls1.setResponse(BookingResponse.ACCEPTED);
	}

	private void setupBkngs1_BkngsDtls2() {
		bkngs1_bkngsDtls2.setBooking(bkngs1);
		bkngs1_bkngsDtls2.setEmployee(emp);
		bkngs1_bkngsDtls2.setPatient(null);
		bkngs1_bkngsDtls2.setResponse(BookingResponse.NO_RESPONSE);
	}

	private void setupBkngs1_BkngsDtls3() {
		bkngs1_bkngsDtls3.setBooking(bkngs1);
		bkngs1_bkngsDtls3.setEmployee(null);
		bkngs1_bkngsDtls3.setPatient(pat1);
		bkngs1_bkngsDtls3.setResponse(BookingResponse.NO_RESPONSE);
	}

	private void setupBkngs2_BkngsDtls4() {
		bkngs2_bkngsDtls4.setBooking(bkngs2);
		bkngs2_bkngsDtls4.setEmployee(empBoss);
		bkngs2_bkngsDtls4.setResponse(BookingResponse.ACCEPTED);
	}

	private void setupBkngs2_BkngsDtls5() {
		bkngs2_bkngsDtls5.setBooking(bkngs2);
		bkngs2_bkngsDtls5.setPatient(pat2);
		bkngs2_bkngsDtls5.setResponse(BookingResponse.REJECTED);
	}
	
	
	private void setupBkngs2_bkngsDtls6() {
		bkngs2_bkngsDtls6.setBooking(bkngs2);
		bkngs2_bkngsDtls6.setPatient(pat1);
		bkngs2_bkngsDtls6.setResponse(BookingResponse.NO_RESPONSE);
	}	
	
	private void setupBkngs3_bkngsDtls7() {
		bkngs3_bkngsDtls7.setBooking(bkngs3);
		bkngs3_bkngsDtls7.setPatient(pat1);
		bkngs3_bkngsDtls7.setResponse(BookingResponse.NO_RESPONSE);
	}	

	private void setupBkngs1_NtfsnStp1() {
		bkngs1_ntfsnStp1.setRecurringTime(3000l);
		bkngs1_ntfsnStp1.setContent("notification content for bk1");
		bkngs1_ntfsnStp1.setEmail(true);
	}

	private void setupBkngs2_NtfsnStp2() {
		bkngs2_ntfsnStp2.setRecurringTime(3000l);
		bkngs2_ntfsnStp2.setContent("notification content for bk2");
		bkngs2_ntfsnStp2.setEmail(true);
		bkngs2_ntfsnStp2.setSms(true);
	}

	private void setupEmpBoss() {
		empBoss.setName("testEmpBoss");
		empBoss.setEmail("testBoss@testBoss.hu");
		empBoss.setUsername("testerBoss");
		empBoss.setPassword(codePassword("abc123Boss"));
		empBoss.setPersonalId("emp01Boss");
		empBoss.setPhoneNumber(123l);
	}

	private void setupEmp() {
		emp.setName("testEmp");
		emp.setEmail("test@test.hu");
		emp.setUsername("tester");
		emp.setPassword(codePassword("abc123"));
		emp.setPersonalId("emp01");
		emp.setPhoneNumber(123l);
		emp.setReportsTo(empBoss);
	}

	private void setupPat1() {
		pat1.setName("testPat");
		pat1.setEmail("testPat@testPat.hu");
		pat1.setUsername("testerPat");
		pat1.setPassword(codePassword("abc123"));
		pat1.setMedicalId(123l);
		pat1.setCounselor(emp);
		pat1.setPhoneNumber(123l);
	}

	private void setupPat2() {
		pat2.setName("testPat2");
		pat2.setEmail("testPat2@testPat2.hu");
		pat2.setUsername("testerPat2");
		pat2.setPassword(codePassword("abc1232"));
		pat2.setMedicalId(1232l);
		pat2.setCounselor(empBoss);
		pat2.setPhoneNumber(123l);
	}

	private void setupPat3() {
		pat3.setName("testPat3");
		pat3.setEmail("testPat3@testPat3.hu");
		pat3.setUsername("testerPat3");
		pat3.setPassword(codePassword("aaaaa"));
		pat3.setMedicalId(424l);
		pat3.setCounselor(empBoss);
		pat3.setPhoneNumber(333l);
	}

	public void setupStarterData() {

		clearDB();

		setupEmpBoss();
		setupEmp();
		setupPat1();
		setupPat2();
		setupPat3();
		setupBkngs1();
		setupBkngs2();
		setupBkngs3();
		
		setupListOfBookingDetails();
		
		setupBkngs1_BkngsDtls1();
		setupBkngs1_BkngsDtls2();
		setupBkngs1_BkngsDtls3();
		setupBkngs2_BkngsDtls4();
		setupBkngs2_BkngsDtls5();
		setupBkngs2_bkngsDtls6();
		setupBkngs3_bkngsDtls7();
		
		setupBkngs1_NtfsnStp1();
		setupBkngs2_NtfsnStp2();	

		empDao.saveEmployee(emp);
		empDao.saveEmployee(empBoss);

		patDao.savePatient(pat1);
		patDao.savePatient(pat2);
		patDao.savePatient(pat3);

		bkngsDao.saveBooking(bkngs1);
		bkngsDao.saveBooking(bkngs2);
		bkngsDao.saveBooking(bkngs3);
		
		bkngs1.setNotificationId(bkngs1_ntfsnStp1);
		bkngs1_ntfsnStp1.setBoookingNotifId(bkngs1);

		bkngs2.setNotificationId(bkngs2_ntfsnStp2);
		bkngs2_ntfsnStp2.setBoookingNotifId(bkngs2);
		
		bkngsDao.updateBooking(bkngs1);
		bkngsDao.updateBooking(bkngs2);

		bkngsDtlsDao.saveBookingDetails(bkngs1_bkngsDtls1);
		bkngsDtlsDao.saveBookingDetails(bkngs1_bkngsDtls2);
		bkngsDtlsDao.saveBookingDetails(bkngs1_bkngsDtls3);
		bkngsDtlsDao.saveBookingDetails(bkngs2_bkngsDtls4);
		bkngsDtlsDao.saveBookingDetails(bkngs2_bkngsDtls5);
		bkngsDtlsDao.saveBookingDetails(bkngs2_bkngsDtls6);
		bkngsDtlsDao.saveBookingDetails(bkngs3_bkngsDtls7);

	}

	private void clearDB() {

		bkngsDtlsDao.clearBookingDetails();
		ntfsnStpDao.clearNotificationSetup();
		bkngsDao.clearBookings();
		empDao.clearEmployee();
		patDao.clearPatient();

		if(listOfBookingDetails!=null)
		{listOfBookingDetails.clear();}
	}

	private String codePassword(String in) {
		return passwordEncoder.encode(in);
	}

}

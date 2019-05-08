package org.Backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.Backend.Config.Config;
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
import org.Backend.Utilities.ReportCreator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.itextpdf.text.DocumentException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
@WebAppConfiguration
public class BookingsSystemTest {

	@Autowired
	private Bookings bkngs1;

	@Autowired
	private Bookings bkngs2;
	
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
	private NotificationSetup bkngs1_ntfsnStp1;

	@Autowired
	private NotificationSetup bkngs2_ntfsnStp2;

	@Autowired
	private EmployeeDao empDao;

	@Autowired
	private PatientDao patDao;
	
	@Autowired
	private BookingsDao bkngsDao;
	
	@Autowired
	private BookingDetailsDao bkngsDtlsDao;
	
	@Autowired
	private NotificationSetupDao ntfsnStpDao;

	@Autowired
	private Employee emp;

	@Autowired
	private Employee empBoss;

	@Autowired
	private Patient pat1;

	@Autowired
	private Patient pat2;
	
	private List<BookingDetails> listOfBookingDetails;

	
	@Before
	public void setupEmp() {
		emp.setName("testEmp");
		emp.setEmail("test@test.hu");
		emp.setUsername("tester");
		emp.setPassword("abc123");
		emp.setPersonalId("emp01");
		emp.setPhoneNumber(123l);
		emp.setReportsTo(empBoss);
	}

	@Before
	public void setupEmpBoss() {
		empBoss.setName("testEmpBoss");
		empBoss.setEmail("testBoss@testBoss.hu");
		empBoss.setUsername("testerBoss");
		empBoss.setPassword("abc123Boss");
		empBoss.setPersonalId("emp01Boss");
	}

	@Before
	public void setupPat1() {
		pat1.setName("testPat1");
		pat1.setEmail("testPat1@testPat1.hu");
		pat1.setUsername("testerPat1");
		pat1.setPassword("abc1231");
		pat1.setMedicalId(1231l);
	}

	@Before
	public void setupPat2() {
		pat2.setName("testPat2");
		pat2.setEmail("testPat2@testPat2.hu");
		pat2.setUsername("testerPat2");
		pat2.setPassword("abc1232");
		pat2.setMedicalId(1232l);
	}

	@Before
	public void setupBkngs1() {
		bkngs1.setTitle("booking1");
		bkngs1.setStart(LocalDateTime.of(2019,Month.APRIL, 11, 14, 00));
		bkngs1.setEnd(LocalDateTime.of(2019,Month.APRIL, 11, 15, 00));
		bkngs1.setRoom("room1");
		bkngs1.setArchived(false);		
		
		// nem állítunk be booking oldalról listát! bkngs1.setBookingDetails(listOfBookingDetails);
	}

	@Before
	public void setupBkngs2() {
		bkngs2.setTitle("booking2");
		bkngs2.setStart(LocalDateTime.of(2019,Month.APRIL, 16, 12, 00));
		bkngs2.setEnd(LocalDateTime.of(2019,Month.APRIL, 16, 13, 00));
		bkngs2.setRoom("room2");
		bkngs2.setArchived(true);
	}

	@Before
	public void setupListOfBookingDetails() {
	listOfBookingDetails= new ArrayList<BookingDetails>();
	listOfBookingDetails.add(bkngs1_bkngsDtls1);
	listOfBookingDetails.add(bkngs1_bkngsDtls2);
	listOfBookingDetails.add(bkngs1_bkngsDtls3);
	}
	
	@Before
	public void setupBkngs1_BkngsDtls1() {
		bkngs1_bkngsDtls1.setBooking(bkngs1);
		bkngs1_bkngsDtls1.setEmployee(empBoss);
		bkngs1_bkngsDtls1.setPatient(null);
	}

	@Before
	public void setupBkngs1_BkngsDtls2() {
		bkngs1_bkngsDtls2.setBooking(bkngs1);
		bkngs1_bkngsDtls2.setEmployee(emp);
		bkngs1_bkngsDtls2.setPatient(null);
		bkngs1_bkngsDtls2.setResponse(BookingResponse.ACCEPTED);
	}

	@Before
	public void setupBkngs1_BkngsDtls3() {
		bkngs1_bkngsDtls3.setBooking(bkngs1);
		bkngs1_bkngsDtls3.setEmployee(null);
		bkngs1_bkngsDtls3.setPatient(pat1);
		bkngs1_bkngsDtls3.setResponse(BookingResponse.ACCEPTED);
	}

	@Before
	public void setupBkngs2_BkngsDtls4() {
		bkngs2_bkngsDtls4.setBooking(bkngs2);
		bkngs2_bkngsDtls4.setEmployee(empBoss);
		bkngs2_bkngsDtls4.setResponse(BookingResponse.ACCEPTED);
	}

	@Before
	public void setupBkngs2_BkngsDtls5() {
		bkngs2_bkngsDtls5.setBooking(bkngs2);
		bkngs2_bkngsDtls5.setPatient(pat2);
		bkngs2_bkngsDtls5.setResponse(BookingResponse.REJECTED);
	}

	@Before
	public void setupBkngs1_NtfsnStp1() {
		bkngs1_ntfsnStp1.setRecurringTime(3000l);
		bkngs1_ntfsnStp1.setContent("notification content for bk1");
		bkngs1_ntfsnStp1.setEmail(true);
	}

	@Before
	public void setupBkngs2_NtfsnStp2() {
		bkngs2_ntfsnStp2.setRecurringTime(3000l);
		bkngs2_ntfsnStp2.setContent("notification content for bk2");
		bkngs2_ntfsnStp2.setEmail(true);
		bkngs2_ntfsnStp2.setSms(true);
	}
	
	@Before
	@After
	public void clearTables() {
		assertNotNull(empDao);
		assertNotNull(patDao);		
		assertNotNull(bkngsDao);
		assertNotNull(bkngsDtlsDao);
		assertNotNull(ntfsnStpDao);
		

		bkngsDtlsDao.clearBookingDetails();
		ntfsnStpDao.clearNotificationSetup();
		bkngsDao.clearBookings();
		empDao.clearEmployee();
		patDao.clearPatient();
		
		listOfBookingDetails.clear();
	}

	@Test
	public void testAutowired() {
		assertNotNull(bkngs1);
		assertNotNull(bkngs2);
		assertNotNull(bkngs1_bkngsDtls1);
		assertNotNull(bkngs1_bkngsDtls2);
		assertNotNull(bkngs1_bkngsDtls3);
		assertNotNull(bkngs2_bkngsDtls4);
		assertNotNull(bkngs2_bkngsDtls5);
		assertNotNull(bkngs1_ntfsnStp1);
		assertNotNull(bkngs2_ntfsnStp2);
		assertNotNull(empDao);
		assertNotNull(patDao);
		assertNotNull(bkngsDao);
		assertNotNull(bkngsDtlsDao);
		assertNotNull(ntfsnStpDao);
		assertNotNull(emp);
		assertNotNull(empBoss);
		assertNotNull(pat1);
		assertNotNull(pat2);
		assertNotNull(reportCreator);
	}

	@Test
	public void testDaoSaveBkngs() {
		 assertNotNull(bkngsDao);
		 bkngsDao.saveBooking(bkngs1);
		 final Long bkngsId1=bkngs1.getId();
		 assertNotNull(bkngsDao.selectBookingById(bkngsId1));
		 assertTrue(bkngsDao.selectBookingById(bkngsId1).getTitle().equals("booking1"));
	}
	
	@Test
	public void testDaoSaveBkngsDtls() {
		 assertNotNull(bkngsDtlsDao);
		 assertTrue(bkngsDtlsDao.selectAllBookingDetails().isEmpty()); 
		 bkngsDtlsDao.saveBookingDetails(bkngs1_bkngsDtls1);
		 bkngsDtlsDao.saveBookingDetails(bkngs1_bkngsDtls2);
		 assertTrue(bkngsDtlsDao.selectAllBookingDetails().size()==2); 
		 
		 final Long bkngsDtlsId1=bkngs1_bkngsDtls1.getId();
		 final Long bkngsDtlsId2=bkngs1_bkngsDtls2.getId();
		 
		 assertNotNull(bkngsDtlsDao.selectBookingDetailsById(bkngsDtlsId1));
		 assertNotNull(bkngsDtlsDao.selectBookingDetailsById(bkngsDtlsId2));
		 assertTrue(bkngsDtlsDao.selectBookingDetailsById(bkngsDtlsId1).getEmployee().getName().equals("testEmpBoss"));
		 assertTrue(bkngsDtlsDao.selectBookingDetailsById(bkngsDtlsId2).getEmployee().getName().equals("testEmp"));
	}
	
	@Test
	public void testDaoSaveBkngsNtfsnStp() {
		 assertNotNull(ntfsnStpDao);

		 bkngsDao.saveBooking(bkngs1);
		 bkngs1.setNotificationId(bkngs1_ntfsnStp1); 
		 bkngs1_ntfsnStp1.setBoookingNotifId(bkngs1);
		 bkngsDao.updateBooking(bkngs1);
		 
		 final Long ntfsnStpId1= ntfsnStpDao.selectAllNotificationSetup().get(0).getId();
		 
		 assertTrue(ntfsnStpDao.selectNotificationSetupById(ntfsnStpId1)!=null); 
		 assertTrue(ntfsnStpDao.selectNotificationSetupById(ntfsnStpId1).getContent().equals("notification content for bk1"));
		 
	}

	@Test
	public void testDaoUpdateBookingTitle() {
		 assertNotNull(bkngsDao);
		 assertTrue(bkngsDao.selectAllBooking().size()==0);
		 bkngsDao.saveBooking(bkngs1);
		 final Long bkngsId1=bkngs1.getId();
		 assertTrue(bkngsDao.selectBookingByTitle("booking1").size()==1);
		 bkngsDao.updateBookingTitle(bkngsId1, "UPDATED_");
		 assertTrue(bkngsDao.selectBookingByTitle("UPDATED_").size()==1);
	}
	
	@Test
	public void testDaoUpdateBookingReport() throws DocumentException, URISyntaxException, IOException {
		 assertNotNull(bkngsDao);
		 assertTrue(bkngsDao.selectAllBooking().size()==0);	
		 bkngsDao.saveBooking(bkngs1);
		 
		 bkngs1.setNotificationId(bkngs1_ntfsnStp1); 
		 bkngs1_ntfsnStp1.setBoookingNotifId(bkngs1);
		 bkngs1_bkngsDtls1.setBooking(bkngs1);
		 bkngs1_bkngsDtls2.setBooking(bkngs1);
		 bkngs1_bkngsDtls3.setBooking(bkngs1);		 
		 bkngsDtlsDao.saveBookingDetails(bkngs1_bkngsDtls1);
		 bkngsDtlsDao.saveBookingDetails(bkngs1_bkngsDtls2);
		 bkngsDtlsDao.saveBookingDetails(bkngs1_bkngsDtls3);
		 bkngsDao.updateBooking(bkngs1);	
		 
		 final Long bkngsId2=bkngs1.getId();
		 bkngs1 = bkngsDao.selectBookingById(bkngsId2);
		 
		 bkngs1.setReport(reportCreator.retrieveAsByte(bkngs1.getBookingDetails(), emp, "We had a great conversation, and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum."));		 
		 bkngsDao.updateBooking(bkngs1);
		 
		 final Long bkngsId3=bkngs1.getId();		 
		 assertEquals(true, reportCreator.generateFileFromByte(bkngsDao.selectBookingById(bkngsId3).getReport()));
	}

	@Test
	public void testDaoUpdateBookingDetailsBookings() {
		assertNotNull(bkngsDao);
		assertNotNull(bkngsDtlsDao);
		
		bkngsDao.saveBooking(bkngs1);
		assertTrue(bkngsDtlsDao.selectAllBookingDetails().size()==0);
		bkngsDtlsDao.saveBookingDetails(bkngs1_bkngsDtls1);
		assertTrue(bkngsDtlsDao.selectAllBookingDetails().size()==1);
		
		 final Long bkngsId1=bkngs1.getId();		
		 bkngs1 = bkngsDao.selectBookingById(bkngsId1);
		 
		 assertTrue(bkngs1.getBookingDetails().get(0).getEmployee().equals(bkngs1_bkngsDtls1.getEmployee()));
		 
		 bkngs1_bkngsDtls1.setBooking(null);
		 bkngsDtlsDao.updateBookingDetails(bkngs1_bkngsDtls1);
		 
		 final Long bkngsId2=bkngs1.getId();		
		 bkngs1 = bkngsDao.selectBookingById(bkngsId2);
		 
		 
		 assertTrue(bkngs1.getBookingDetails().isEmpty());
		 
		 bkngsDtlsDao.saveBookingDetails(bkngs1_bkngsDtls2);
		 
		 final Long bkngsId3=bkngs1.getId();		
		 bkngs1 = bkngsDao.selectBookingById(bkngsId3);
		 
		 assertTrue(bkngs1.getBookingDetails().get(0).getEmployee().equals(bkngs1_bkngsDtls2.getEmployee()));
	}
	
	@Test
	public void testDaoUpdateNotificationSetupBookings() {
		assertNotNull(bkngsDao);
		 assertTrue(bkngsDao.selectAllBooking().size()==0);	
		 bkngsDao.saveBooking(bkngs1);
		 
		 bkngs1.setNotificationId(bkngs1_ntfsnStp1); 
		 bkngs1_ntfsnStp1.setBoookingNotifId(bkngs1);
		 bkngsDao.updateBooking(bkngs1);
		 
		 final Long bkngsId1=bkngs1.getId();		
		 bkngs1 = bkngsDao.selectBookingById(bkngsId1);
		 
		 assertTrue(bkngs1.getNotificationId().equals(bkngs1_ntfsnStp1));

		 ntfsnStpDao.deleteNotificationSetup(bkngs1.getId());
		 
		 bkngs1.setNotificationId(bkngs2_ntfsnStp2); 
		 bkngs2_ntfsnStp2.setBoookingNotifId(bkngs1);
		 bkngsDao.updateBooking(bkngs1);
		 
		 assertTrue(bkngs1.getNotificationId().equals(bkngs2_ntfsnStp2));
		 
	}
	
	@Test
	public void testDaoDeleteBkngs() {
		 assertNotNull(bkngsDao);
		 assertTrue(bkngsDao.selectAllBooking().size()==0);
		 bkngsDao.saveBooking(bkngs1);
		 final Long bkngsId1=bkngs1.getId();
		 bkngsDao.saveBooking(bkngs1);
		 final Long bkngsId2=bkngs1.getId();
		 assertTrue(bkngsDao.selectBookingByTitle("booking1").size()==2);
		 bkngsDao.deleteBooking(bkngsId1);
		 List<Bookings> bkngsList = bkngsDao.selectBookingByTitle("booking1");
		 assertTrue(bkngsList.size()==1 && bkngsList.get(0).getId()==bkngsId2);
	}
	
	@Test
	public void testDaoDeleteBkngsDtls() {
		 assertNotNull(bkngsDtlsDao);
		 assertTrue(bkngsDtlsDao.selectAllBookingDetails().size()==0);
		 bkngsDtlsDao.saveBookingDetails(bkngs1_bkngsDtls1);
		 final Long bkngsDtls1Id1=bkngs1_bkngsDtls1.getId();
		 bkngsDtlsDao.saveBookingDetails(bkngs1_bkngsDtls1);
		 final Long bkngsDtls1Id2=bkngs1_bkngsDtls1.getId();
		 assertTrue(bkngsDtlsDao.selectBookingDetailsByBooking(bkngs1).size()==2);
		 bkngsDtlsDao.deleteBookingDetails(bkngsDtls1Id1);
		 List<BookingDetails> bkngsDtlsList = bkngsDtlsDao.selectBookingDetailsByBooking(bkngs1);
		 assertTrue(bkngsDtlsList.size()==1 && bkngsDtlsList.get(0).getId()==bkngsDtls1Id2);
	}
	
	@Test
	public void testDaoDeleteNtfsn() {
		 assertNotNull(ntfsnStpDao);
		 assertTrue(ntfsnStpDao.selectAllNotificationSetup().size()==0);
		 bkngsDao.saveBooking(bkngs1);
		 bkngs1.setNotificationId(bkngs1_ntfsnStp1); 
		 bkngs1_ntfsnStp1.setBoookingNotifId(bkngs1);
		 bkngsDao.updateBooking(bkngs1);
		 
		 //ntfsnStpDao.saveNotificationSetup(bkngs1_ntfsnStp1);
		 final Long ntfsnStpId1=bkngs1_ntfsnStp1.getId();

		 bkngsDao.saveBooking(bkngs2);
		 bkngs2.setNotificationId(bkngs2_ntfsnStp2); 
		 bkngs2_ntfsnStp2.setBoookingNotifId(bkngs2);
		 bkngsDao.updateBooking(bkngs2);
		 
		 final Long ntfsnStpId2=bkngs2_ntfsnStp2.getId();
		 
		 assertTrue(ntfsnStpDao.selectAllNotificationSetup().size()==2);
		 ntfsnStpDao.deleteNotificationSetup(ntfsnStpId1);
		 List<NotificationSetup> ntfsList = ntfsnStpDao.selectAllNotificationSetup();
		 assertTrue(ntfsList.size()==1 && ntfsList.get(0).getId()==ntfsnStpId2);
	}
	


}

package org.Backend;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import org.Backend.Config.Config;
import org.Backend.DAOs.EmployeeDao;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class BookingsSystemTest {

	@Autowired
	private Bookings bkngs1;

	@Autowired
	private Bookings bkngs2;

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
	private Employee emp;

	@Autowired
	private Employee empBoss;

	@Autowired
	private Patient pat1;

	@Autowired
	private Patient pat2;

	@Autowired
	private ReportCreator reportCreator;

	@Before
	public void setupBkngs1() {
		bkngs1.setTitle("booking1");
		bkngs1.setStart(LocalDateTime.now());
		bkngs1.setEnd(LocalDateTime.now().plusHours(12));
		bkngs1.setRoom("room1");
		bkngs1.setArchived(false);
	}

	@Before
	public void setupBkngs2() {
		bkngs2.setTitle("booking2");
		bkngs2.setStart(LocalDateTime.now().plusHours(12));
		bkngs2.setEnd(LocalDateTime.now().plusHours(24));
		bkngs2.setRoom("room2");
		bkngs2.setArchived(true);
	}

	@Before
	public void setupBkngs1_BkngsDtls1() {
		bkngs1_bkngsDtls1.setBooking(bkngs1);
		bkngs1_bkngsDtls1.setEmployee(empBoss);
	}

	@Before
	public void setupBkngs1_BkngsDtls2() {
		bkngs1_bkngsDtls2.setBooking(bkngs1);
		bkngs1_bkngsDtls2.setEmployee(emp);
		bkngs1_bkngsDtls2.setResponse(BookingResponse.ACCEPTED);
	}

	@Before
	public void setupBkngs1_BkngsDtls3() {
		bkngs1_bkngsDtls3.setBooking(bkngs1);
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
		bkngs1_ntfsnStp1.setBoookingNotifId(bkngs1);
		bkngs1_ntfsnStp1.setRecurringTime(3000l);
		bkngs1_ntfsnStp1.setContent("notification content for bk1");
		bkngs1_ntfsnStp1.setEmail(true);
	}

	@Before
	public void setupBkngs2_NtfsnStp2() {
		bkngs2_ntfsnStp2.setBoookingNotifId(bkngs2);
		bkngs2_ntfsnStp2.setRecurringTime(3000l);
		bkngs2_ntfsnStp2.setContent("notification content for bk2");
		bkngs2_ntfsnStp2.setEmail(true);
		bkngs2_ntfsnStp2.setSms(true);
	}

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

	@After
	public void clearTables() {
		assertNotNull(empDao);
		assertNotNull(patDao);
		empDao.clearEmployee();
		patDao.clearPatient();
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
		assertNotNull(emp);
		assertNotNull(empBoss);
		assertNotNull(pat1);
		assertNotNull(pat2);
		assertNotNull(reportCreator);
	}

	@Test
	public void testDaoSaveEmp() {
		// assertNotNull(empDao);
		// assertTrue(empDao.selectEmployeeByName("testEmp").isEmpty());
		// empDao.saveEmployee(emp);
		// assertNotNull(empDao.selectEmployeeByName("testEmp").get(0));
		// assertTrue(empDao.selectEmployeeByName("testEmp").get(0).getName().equals("testEmp"));
	}

	@Test
	public void testDaoDeleteEmp() {
		// assertTrue(empDao.selectAllEmployee().size()==0);
		// assertNotNull(empDao);
		// assertTrue(empDao.selectEmployeeByName("testEmp").isEmpty());
		// empDao.saveEmployee(emp);
		// final Long id1=emp.getId();
		// empDao.saveEmployee(emp);
		// final Long id2=emp.getId();
		// assertTrue(empDao.selectEmployeeByName("testEmp").size()==2);
		// empDao.deleteEmployee(id1);
		// List<Employee> empList = empDao.selectEmployeeByName("testEmp");
		// assertTrue(empList.size()==1 && empList.get(0).getId()==id2);
	}

	@Test
	public void testDaoUpdateName() {
		// assertNotNull(empDao);
		// assertTrue(empDao.selectAllEmployee().size()==0);
		// assertTrue(empDao.selectEmployeeByName("testEmp").isEmpty());
		// assertTrue(empDao.selectEmployeeByName("NAME_UPDATED").isEmpty());
		//
		// empDao.saveEmployee(emp);
		// empDao.updateEmployeeName("testEmp", "NAME_UPDATED");
		//
		//
		//
		// assertFalse(empDao.selectEmployeeByName("NAME_UPDATED").isEmpty());
		// assertTrue(empDao.selectEmployeeByName("testEmp").isEmpty());
		// assertTrue(empDao.selectEmployeeByName("NAME_UPDATED").get(0).getName().equals("NAME_UPDATED"));
	}

	@Test
	public void testDaoSelectByName() {
		// assertNotNull(empDao);
		// assertTrue(empDao.selectEmployeeByName("testEmp").isEmpty());
		// assertTrue(empDao.selectEmployeeByName("testEmp2").isEmpty());
		// empDao.saveEmployee(emp);
		// empDao.saveEmployee(emp);
		// emp.setName("testEmp2");
		// empDao.saveEmployee(emp);
		// assertTrue(empDao.selectEmployeeByName("testEmp").size()==2);
		// assertTrue(empDao.selectEmployeeByName("testEmp2").size()==1);
	}

	@Test
	public void testDaoSelectById() {
		// assertNotNull(empDao);
		// empDao.saveEmployee(emp);
		// final Long id1=emp.getId();
		// emp.setName("testEmp2");
		// empDao.saveEmployee(emp);
		// final Long id2=emp.getId();
		// assertTrue(empDao.selectEmployeeById(id1).get(0).getName().equals("testEmp"));
		// assertTrue(empDao.selectEmployeeById(id2).get(0).getName().equals("testEmp2"));
	}

	@Test
	public void testDaoUpdateReportsTo() {
		// assertNotNull(empDao);
		// empDao.saveEmployee(emp);
		// final Long id1=emp.getId();
		// assertTrue(empDao.selectEmployeeById(id1).get(0).getReportsTo().equals(empBoss));
		// empDao.updateEmployeeReportsTo(id1, null);
		// assertTrue(empDao.selectEmployeeById(id1).get(0).getReportsTo()==null);
	}

	@Test
	public void testDaoUpdatePhone() {
		// assertNotNull(empDao);
		// empDao.saveEmployee(emp);
		// final Long id1=emp.getId();
		// assertTrue(empDao.selectEmployeeById(id1).get(0).getPhoneNumber().equals(123l));
		// empDao.updateEmployeePhone(id1, 321l);
		// assertTrue(empDao.selectEmployeeById(id1).get(0).getPhoneNumber().equals(321l));
	}

	@Test
	public void testDaoUpdateEmployee() {
		// assertNotNull(empDao);
		// empDao.saveEmployee(emp);
		// final Long id1=emp.getId();
		// assertTrue(empDao.selectEmployeeById(id1).get(0).getPhoneNumber().equals(123l));
		// emp.setPhoneNumber(321l);
		// empDao.updateEmployee(emp);
		// assertTrue(empDao.selectEmployeeById(id1).get(0).getPhoneNumber().equals(321l));
	}

}

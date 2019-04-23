package org.Backend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class AppTest {	
	
		@Autowired
		private H2Test testH2;
			
		@Autowired
		private EmployeeDao empDao;
		
		@Autowired
		private Employee emp;
		
		@Autowired
		private Employee empBoss;
		
		@Autowired
		private Patient pat;
		
		@Autowired
		private Bookings bkngs;
		
		@Autowired
		private BookingDetails bkngsDtls;
		
		@Autowired
		private Message msg;
		
		@Autowired
		private MessageDetails msgDtls;
		
		@Autowired
		private NotificationSetup ntfsnStp;
		
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
		public void setupPat() {
			pat.setName("testPat");
			pat.setEmail("testPat@testPat.hu");
			pat.setUsername("testerPat");
			pat.setPassword("abc123");
			pat.setMedicalId(123l);			
		} 
		
		@Before
		public void setupBkngs() {
			bkngs.setTitle("booking1");
			bkngs.setStart(LocalDateTime.now());
			bkngs.setEnd(LocalDateTime.now().plusHours(12));
			bkngs.setRoom("room1");	
		} 
	/*
		@Before
		public void setupBkngsDtls() {
			bkngsDtls.("testEmp");
			bkngsDtls.("test@test.hu");
			bkngsDtls.("tester");
			bkngsDtls.("abc123");
			bkngsDtls.(123l);			
		} 
		*/
			
		@Before
		public void setupMsg() {
			msg.setSentAt(LocalDateTime.now());
			msg.setContent("message cntent");		
		} 
		/*
		@Before
		public void setupMsgDtls() {
			pat.setName("testEmp");
			pat.setEmail("test@test.hu");
			pat.setUsername("tester");
			pat.setPassword("abc123");
			pat.setMedicalId(123l);			
		} 
		*/
		@Before
		public void setupNtfsnStp() {
			ntfsnStp.setRecurringTime(3000l);
			ntfsnStp.setContent("notification content");
			ntfsnStp.setEmail(true);		
		} 
		
		
		@After
		public void clearTables() {
			assertNotNull(empDao);
			empDao.clearEmployee();		
		} 
		
		@Test
		public void testAutowired() {
			assertNotNull(testH2);
			assertNotNull(empDao);
			assertNotNull(emp);
			assertNotNull(pat);
			assertNotNull(bkngs);
			assertNotNull(bkngsDtls);
			assertNotNull(msg);
			assertNotNull(msgDtls);			
			assertNotNull(ntfsnStp);
		}
			
		@Test
		public void testH2() {
		assertNotNull(testH2);
		testH2.callMeH2();			
		}
		
		@Test
		public void testDaoSaveEmp() {
			assertNotNull(empDao);	
			assertTrue(empDao.selectEmployeeByName("testEmp").isEmpty());
			empDao.saveEmployee(emp);
			assertNotNull(empDao.selectEmployeeByName("testEmp").get(0));
			assertTrue(empDao.selectEmployeeByName("testEmp").get(0).getName().equals("testEmp"));
			}
		
		@Test
		public void testDaoDeleteEmp() {
			assertTrue(empDao.selectAllEmployee().size()==0);
			assertNotNull(empDao);
			assertTrue(empDao.selectEmployeeByName("testEmp").isEmpty());
			empDao.saveEmployee(emp);	
			final Long id1=emp.getId();
			empDao.saveEmployee(emp);	
			final Long id2=emp.getId();
			assertTrue(empDao.selectEmployeeByName("testEmp").size()==2);
			empDao.deleteEmployee(id1);
			List<Employee> empList = empDao.selectEmployeeByName("testEmp");
			assertTrue(empList.size()==1 && empList.get(0).getId()==id2);
			}
			
		@Test
		public void testDaoUpdateName() {
			assertNotNull(empDao);
			assertTrue(empDao.selectAllEmployee().size()==0);
			assertTrue(empDao.selectEmployeeByName("testEmp").isEmpty());
			assertTrue(empDao.selectEmployeeByName("NAME_UPDATED").isEmpty());
			
			empDao.saveEmployee(emp);
			empDao.updateEmployeeName("testEmp", "NAME_UPDATED");
			
			
			
			assertFalse(empDao.selectEmployeeByName("NAME_UPDATED").isEmpty());
			assertTrue(empDao.selectEmployeeByName("testEmp").isEmpty());
			assertTrue(empDao.selectEmployeeByName("NAME_UPDATED").get(0).getName().equals("NAME_UPDATED"));
			}
		
		@Test
		public void testDaoSelectByName() {
			assertNotNull(empDao);
			assertTrue(empDao.selectEmployeeByName("testEmp").isEmpty());
			assertTrue(empDao.selectEmployeeByName("testEmp2").isEmpty());
			empDao.saveEmployee(emp);
			empDao.saveEmployee(emp);
			emp.setName("testEmp2");
			empDao.saveEmployee(emp);			
			assertTrue(empDao.selectEmployeeByName("testEmp").size()==2);
			assertTrue(empDao.selectEmployeeByName("testEmp2").size()==1);
			}
		
		@Test
		public void testDaoSelectById() {
			assertNotNull(empDao);
			empDao.saveEmployee(emp);
			final Long id1=emp.getId();
			emp.setName("testEmp2");
			empDao.saveEmployee(emp);
			final Long id2=emp.getId();
			assertTrue(empDao.selectEmployeeById(id1).get(0).getName().equals("testEmp"));
			assertTrue(empDao.selectEmployeeById(id2).get(0).getName().equals("testEmp2"));
			}
		
		@Test
		public void testDaoUpdateReportsTo() {
			assertNotNull(empDao);
			empDao.saveEmployee(emp);
			final Long id1=emp.getId();
			assertTrue(empDao.selectEmployeeById(id1).get(0).getReportsTo().equals(empBoss));
			empDao.updateEmployeeReportsTo(id1, null);
			assertTrue(empDao.selectEmployeeById(id1).get(0).getReportsTo()==null);
			}
		
		@Test
		public void testDaoUpdatePhone() {
			assertNotNull(empDao);
			empDao.saveEmployee(emp);
			final Long id1=emp.getId();
			assertTrue(empDao.selectEmployeeById(id1).get(0).getPhoneNumber().equals(123l));
			empDao.updateEmployeePhone(id1, 321l);
			assertTrue(empDao.selectEmployeeById(id1).get(0).getPhoneNumber().equals(321l));
			}
	
}



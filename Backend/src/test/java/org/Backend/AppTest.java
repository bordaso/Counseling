package org.Backend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class AppTest {	
	
		@Autowired
		private H2Test testMeH2;
			
		@Autowired
		private EmployeeDao empDao;
		
		@Autowired
		private Employee emp;
		
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
		} 
		
		@Before
		public void setupPat() {
			pat.setName("testEmp");
			pat.setEmail("test@test.hu");
			pat.setUsername("tester");
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
		
		@Test
		public void testAutowired() {
			assertNotNull(testMeH2);
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
		assertNotNull(testMeH2);
		testMeH2.callMeH2();			
		}
		
		@Test
		public void testDaoSave() {
			assertNotNull(empDao);		
			empDao.saveEmployee(emp);
			assertNotNull(empDao.selectEmployeeByName("testEmp").get(0));
			assertTrue(empDao.selectEmployeeByName("testEmp").get(0).getName().equals("testEmp"));
			}
		
		@Test
		public void testDaoDrop() {
			assertNotNull(empDao);
			assertTrue(empDao.selectEmployeeByName("testEmp").isEmpty());
			empDao.saveEmployee(emp);	
			empDao.saveEmployee(emp);	
			assertTrue(empDao.selectEmployeeByName("testEmp").size()==2);
			empDao.deleteEmployee(1l);
			List<Employee> empList = empDao.selectEmployeeByName("testEmp");
			assertTrue(empList.size()==1 && empList.get(0).getId()==2l);
			}
			
		@Test
		public void testDaoUpdate() {
			assertNotNull(empDao);
			assertTrue(empDao.selectEmployeeByName("testEmp").isEmpty());
			empDao.saveEmployee(emp);
			assertFalse(empDao.selectEmployeeByName("testEmp").isEmpty());
			empDao.updateEmployee("testEmp", "NAME_UPDATED");
			assertFalse(empDao.selectEmployeeByName("NAME_UPDATED").isEmpty());
			assertTrue(empDao.selectEmployeeByName("NAME_UPDATED").get(0).getName().equals("NAME_UPDATED"));
			}
		
		 
	
	
}



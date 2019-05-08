package org.Backend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.Backend.Config.Config;
import org.Backend.DAOs.EmployeeDao;
import org.Backend.DAOs.PatientDao;
import org.Backend.Entities.Employee;
import org.Backend.Entities.Patient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
@WebAppConfiguration
public class PatientTest {	
			
		@Autowired
		private PatientDao patDao;
		
		@Autowired
		private EmployeeDao empDao;
		
		@Autowired
		private Employee emp;
		
		@Autowired
		private Employee empBoss;
		
		@Autowired
		private Patient pat;
		
		@Autowired
		private Patient pat2;
		
		@Autowired
		private Patient pat3;
		
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
			empBoss.setPhoneNumber(123l);
		} 
		
		@Before
		public void setupPat() {
			pat.setName("testPat");
			pat.setEmail("testPat@testPat.hu");
			pat.setUsername("testerPat");
			pat.setPassword("abc123");
			pat.setMedicalId(123l);
			pat.setCounselor(emp);
			pat.setPhoneNumber(123l);
		} 
		
		@Before
		public void setupPat2() {
			pat2.setName("testPat");
			pat2.setEmail("testPat2@testPat2.hu");
			pat2.setUsername("testerPat2");
			pat2.setPassword("abc1232");
			pat2.setMedicalId(1232l);
			pat2.setCounselor(empBoss);
			pat2.setPhoneNumber(123l);
		} 
		
		@Before
		public void setupPat3() {
			pat3.setName("testPat3");
			pat3.setEmail("testPat3@testPat3.hu");
			pat3.setUsername("testerPat3");
			pat3.setPassword("aaaaa");
			pat3.setMedicalId(424l);
			pat3.setCounselor(empBoss);
			pat3.setPhoneNumber(333l);
		} 
		
		@Before
		@After
		public void clearTables() {
			assertNotNull(patDao);
			assertNotNull(empDao);
			patDao.clearPatient();	
			empDao.clearEmployee();
		} 
		
		@Test
		public void testAutowired() {
			assertNotNull(patDao);
			assertNotNull(emp);
			assertNotNull(empBoss);
			assertNotNull(pat);
			assertNotNull(pat2);
			assertNotNull(pat3);
		}
		
		@Test
		public void testDaoSavePat() {
			assertNotNull(patDao);	
			assertTrue(patDao.selectPatientByName("testPat").isEmpty());
			patDao.savePatient(pat);
			assertNotNull(patDao.selectPatientByName("testPat").get(0));
			assertTrue(patDao.selectPatientByName("testPat").get(0).getName().equals("testPat"));
			}
		
		@Test
		public void testDaoDeletePat() {
			assertTrue(patDao.selectAllPatient().size()==0);
			assertNotNull(patDao);
			assertTrue(patDao.selectPatientByName("testPat").isEmpty());
			patDao.savePatient(pat);	
			final Long patId1=pat.getId();
			patDao.savePatient(pat2);	
			final Long patId2=pat2.getId();
			assertTrue(patDao.selectPatientByName("testPat").size()==2);
			patDao.deletePatient(patId1);
			List<Patient> empList = patDao.selectPatientByName("testPat");
			assertTrue(empList.size()==1 && empList.get(0).getId()==patId2);
			}
			
		@Test
		public void testDaoUpdateName() {
			assertNotNull(patDao);
			assertTrue(patDao.selectAllPatient().size()==0);
			assertTrue(patDao.selectPatientByName("testPat").isEmpty());
			assertTrue(patDao.selectPatientByName("NAME_UPDATED").isEmpty());
			
			patDao.savePatient(pat);
			patDao.updatePatientName("testPat", "NAME_UPDATED");
			
			
			
			assertFalse(patDao.selectPatientByName("NAME_UPDATED").isEmpty());
			assertTrue(patDao.selectPatientByName("testPat").isEmpty());
			assertTrue(patDao.selectPatientByName("NAME_UPDATED").get(0).getName().equals("NAME_UPDATED"));
			}
		
		@Test
		public void testDaoSelectByName() {
			assertNotNull(patDao);
			assertTrue(patDao.selectPatientByName("testPat").isEmpty());
			assertTrue(patDao.selectPatientByName("testPat2").isEmpty());
			patDao.savePatient(pat);
			patDao.savePatient(pat2);
			pat3.setName("testPat2");
			patDao.savePatient(pat3);			
			assertTrue(patDao.selectPatientByName("testPat").size()==2);
			assertTrue(patDao.selectPatientByName("testPat2").size()==1);
			}
		
		@Test
		public void testDaoSelectById() {
			assertNotNull(patDao);
			patDao.savePatient(pat);
			final Long patId1=pat.getId();
			pat2.setName("testPat2");
			patDao.savePatient(pat2);
			final Long patId2=pat2.getId();
			assertTrue(patDao.selectPatientById(patId1).get(0).getName().equals("testPat"));
			assertTrue(patDao.selectPatientById(patId2).get(0).getName().equals("testPat2"));
			}
		
		@Test
		public void testDaoUpdateCounselor() {
			assertNotNull(patDao);
			patDao.savePatient(pat);
			final Long patId1=pat.getId();
			patDao.savePatient(pat2);
			final Long patId2=pat2.getId();
			assertTrue(patDao.selectPatientById(patId1).get(0).getCounselor().equals(emp));
			assertTrue(patDao.selectPatientById(patId2).get(0).getCounselor().equals(empBoss));
			
			patDao.updatePatientCounselor(patId2, emp);
			assertTrue(patDao.selectPatientById(patId2).get(0).getCounselor().equals(emp));
			
			assertTrue(empDao.selectEmployeeByName("testEmp").size()==1);
			assertTrue(empDao.selectEmployeeByName("testEmpBoss").size()==1);
			assertTrue(empDao.selectEmployeeByName("testEmp").get(0).getPatientList().size()==2);
			assertTrue(empDao.selectEmployeeByName("testEmpBoss").get(0).getPatientList().size()==0);			
			}
	
		@Test
		public void testDaoUpdatePatient() {
			assertNotNull(patDao);
			patDao.savePatient(pat);
			final Long patId1=pat.getId();
			assertTrue(patDao.selectPatientById(patId1).get(0).getPhoneNumber().equals(123l));
			pat.setPhoneNumber(321l);
			patDao.updatePatient(pat);
			assertTrue(patDao.selectPatientById(patId1).get(0).getPhoneNumber().equals(321l));
			}
		
		@Test
		public void testDaoSelectByUsername() {
			assertNotNull(patDao);
			assertTrue(patDao.selectPatientByUsername("testerPat").isEmpty());
			assertTrue(patDao.selectPatientByUsername("testerPat2").isEmpty());
			assertTrue(patDao.selectPatientByUsername("testerPat3").isEmpty());
			patDao.savePatient(pat);
			patDao.savePatient(pat2);
			patDao.savePatient(pat3);
			
			assertTrue(patDao.selectPatientByUsername("testerPat").size()==1);
			assertTrue(patDao.selectPatientByUsername("testerPat2").size()==1);
			assertTrue(patDao.selectPatientByUsername("testerPat3").size()==1);
			}
		
}



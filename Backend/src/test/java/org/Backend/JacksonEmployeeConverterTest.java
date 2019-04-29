package org.Backend;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.Backend.Config.Config;
import org.Backend.Entities.Employee;
import org.Backend.Entities.Patient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class JacksonEmployeeConverterTest {
	
	@Autowired
	private JacksonEmployeeConverter jec;
	
	@Autowired
	private Employee emp;
	
	@Autowired
	private Employee empBoss;
	
	@Autowired
	private Patient pat;
	
	@Autowired
	private Patient pat2;
	
	@Before
	public void setupEmp() {
		emp.setName("testEmp");
		emp.setEmail("test@test.hu");
		emp.setUsername("tester");
		emp.setPassword("abc123");
		emp.setPersonalId("emp01");		
		emp.setPhoneNumber(123l);
		emp.setReportsTo(empBoss);
		
		List<Patient> patList = new ArrayList<Patient>();
		patList.add(pat);
		patList.add(pat2);
		
		emp.setPatientList(patList);
	} 
	
	@Before
	public void setupEmpBoss() {
		empBoss.setName("testEmpBoss");
		empBoss.setEmail("testBoss@testBoss.hu");
		empBoss.setUsername("testerBoss");
		empBoss.setPassword("abc123Boss");
		empBoss.setPersonalId("emp01Boss");		
		empBoss.setPhoneNumber(123l);
		
		List<Patient> patList = new ArrayList<Patient>();
		patList.add(pat2);
		
		empBoss.setPatientList(patList);
		
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
		pat2.setName("testPat2");
		pat2.setEmail("testPat2@testPat2.hu");
		pat2.setUsername("testerPat2");
		pat2.setPassword("abc1232");
		pat2.setMedicalId(1232l);
		pat2.setCounselor(empBoss);
		pat2.setPhoneNumber(123l);
	} 
	
	
	@Test
	public void testPojoToJson() throws IOException {
		String empJson1 = jec.employeePojoToJson(emp);
		String empJson2 = jec.employeePojoToJson(jec.jsonToEmployeePojo(jec.employeePojoToJson(emp)));
		assertEquals(empJson1, empJson2);
		}
	
	@Test
	public void testJsonToPojo() throws IOException {
		Employee emp2 = jec.jsonToEmployeePojo(jec.employeePojoToJson(emp));
		assertEquals(emp, emp2);
		}
	
	

}

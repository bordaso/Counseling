package org.Backend.Config;

import java.util.List;

import org.Backend.DAOs.EmployeeDao;
import org.Backend.DAOs.PatientDao;
import org.Backend.Entities.Employee;
import org.Backend.Entities.Patient;
import org.Backend.Utilities.BeanUtil;
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
	
	
	public void setupStarterData() {
		setupEmpBoss();
		setupEmp();
		setupPat1();
		setupPat2();
		setupPat3();
		
		clearDB();
		
		empDao.saveEmployee(emp);
		empDao.saveEmployee(empBoss);
		
		patDao.savePatient(pat1);
		patDao.savePatient(pat2);
		patDao.savePatient(pat3);		
	}
	
	
	public void clearDB() {
		patDao.clearPatient();	
		empDao.clearEmployee();		
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
	
	
	private String codePassword(String in) {		
		return passwordEncoder.encode(in);
	}	

}

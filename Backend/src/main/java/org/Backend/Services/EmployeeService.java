package org.Backend.Services;

import javax.annotation.PostConstruct;

import org.Backend.DAOs.EmployeeDao;
import org.Backend.Entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeService {
	@Autowired
	private Employee emp;

	@Autowired
	private EmployeeDao empDao;
	
	@PostConstruct
	private void setup() {
		setupEmp();
//		setupEmpBoss();
//		setupPat();
//		setupPat2();
	}
	
	private void setupEmp() {
		emp.setName("testEmp");
		emp.setEmail("test@test.hu");
		emp.setUsername("testerEmpService");
		emp.setPassword("abc123");
		emp.setPersonalId("emp01");		
		emp.setPhoneNumber(123l);
//		emp.setReportsTo(empBoss);
//		
//		List<Patient> patList = new ArrayList<Patient>();
//		patList.add(pat);
//		patList.add(pat2);
		
//		emp.setPatientList(patList);
		
		empDao.saveEmployee(emp);
	} 
	

	public Employee employeeFinderByUsername(String username) {
		
		System.err.println(username);

		return empDao.selectEmployeetByUsername(username).get(0);
	}
}

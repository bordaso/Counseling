package org.Backend.Services;

import org.Backend.DAOs.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeService {
	
	@Autowired
	private EmployeeDao empDao;
	
	public boolean employeeLogin(String username, String passwordHash){
		
		
		//empDao.sele
		
		System.err.println(username);
		System.err.println(passwordHash);
	        
	        return true;
	    }
}

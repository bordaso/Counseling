package org.Backend;

import java.util.List;


public interface EmployeeDao {
	
	void saveEmployee(Employee input);
	
    void updateEmployee(String oldName, String newName);
    
	List<Employee> selectEmployeeByName(String inputName);
	
	void deleteEmployee(Long id);
	
	void setMyProxy(EmployeeDao proxy);
	
}

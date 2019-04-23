package org.Backend;

import java.util.List;

public interface EmployeeDao {

	void saveEmployee(Employee input);

	void updateEmployeeName(String oldName, String newName);
	
	void updateEmployeeNameWithHQL(String oldName, String newName, Employee emp);

	List<Employee> selectEmployeeByName(String inputName);

	List<Employee> selectEmployeeById(Long id);
	
	List<Employee> selectAllEmployee();

	void deleteEmployee(Long id);
	
	void updateEmployeePhone(Long id, Long newPhone);
	
	void updateEmployeeReportsTo(Long id, Employee newBoss);

	void clearEmployee();	

	void setMyProxy(EmployeeDao proxy);

}

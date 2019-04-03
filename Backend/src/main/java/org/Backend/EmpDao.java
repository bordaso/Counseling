package org.Backend;

import java.util.List;


public interface EmpDao {
	void saveEmployee(Employee input);
    void updateEmployee(String oldName, String newName);
	List<Employee> selectEmployee(String inputName);
	int deleteEmployee(String inputName);
}

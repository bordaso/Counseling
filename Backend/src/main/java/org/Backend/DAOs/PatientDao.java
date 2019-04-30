package org.Backend.DAOs;

import java.util.List;

import org.Backend.Entities.Employee;
import org.Backend.Entities.Patient;

public interface PatientDao {

	void savePatient(Patient input);

	void updatePatientName(String oldName, String newName);
	
	void updatePatientNameWithHQL(String oldName, String newName, Patient emp);
	
	void updatePatient(Patient toBeUpdated);

	List<Patient> selectPatientByName(String inputName);
	
	List<Patient> selectPatientByUsername(String inputUsername);

	List<Patient> selectPatientById(Long id);
	
	List<Patient> selectAllPatient();

	void deletePatient(Long id);
	
	void updatePatientPhone(Long id, Long newPhone);
	
	void updatePatientCounselor(Long id, Employee counselor);

	void clearPatient();	

	void setMyProxy(PatientDao proxy);

}

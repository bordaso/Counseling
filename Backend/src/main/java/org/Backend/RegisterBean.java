package org.Backend;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.Backend.DAOs.EmployeeDao;
import org.Backend.DAOs.PatientDao;
import org.Backend.Entities.BookingDetails;
import org.Backend.Entities.Bookings;
import org.Backend.Entities.Employee;
import org.Backend.Entities.Patient;
import org.Backend.Entities.User;
import org.Backend.Enums.BookingResponse;
import org.Backend.Enums.UserType;
import org.Backend.Services.CommonService;
import org.Backend.Services.EmployeeService;
import org.Backend.Services.PatientService;
import org.Backend.Utilities.BeanUtil;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class RegisterBean extends SpringBeanAutowiringSupport implements Serializable {

	private Map<Bookings, BookingDetails> bookingsMap = new HashMap<Bookings, BookingDetails>();

	private List<Employee> employees;

	private List<Patient> patient;

	private List<Employee> managerSelected = new ArrayList<Employee>();

	private List<Employee> counselorSelected = new ArrayList<Employee>();

	private List<Patient> patientSelected = new ArrayList<Patient>();

	private List<UserType> types = new ArrayList<UserType>();

	private Map<String, String> requestParams;

	@Autowired
	private EmployeeService es;

	@Autowired
	private EmployeeDao ed;

	@Autowired
	private CommonService cs;

	@Autowired
	private PatientService ps;

	@Autowired
	private PatientDao pd;

	@PostConstruct
	public void init() {
		employees = ed.selectAllEmployee();
		patient = pd.selectAllPatient();
		types = Arrays.asList(UserType.values());
	}

	public void submit() {

		requestParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		if (requestParams.get("userswitcher") == null) {
			createPatient();
			clearUp();
			return;
		}

		createEmployee();		
		clearUp();
	}

	private void createEmployee() {
		
		  Employee newEmp = BeanUtil.getBean(Employee.class);  
		  
		  newEmp.setName(requestParams.get("form:name"));
		  newEmp.setEmail(requestParams.get("form:email"));
		  newEmp.setPhoneNumber(Long.valueOf(requestParams.get("form:phonenumber")));
		  newEmp.setPersonalId(requestParams.get("form:personalId"));
		  newEmp.setUsername(requestParams.get("form:username"));
		  newEmp.setPassword(requestParams.get("form:password"));
		  newEmp.setReportsTo(managerSelected.get(0));
		  newEmp.setPatientList(patientSelected);
		  
		  ed.saveEmployee(newEmp);
		  
		  employees = ed.selectAllEmployee();
		  
		  System.err.println(newEmp);
		  
		
//		for (Entry<String, String> x : requestParams.entrySet()) {
//			System.out.println(x.getKey() + " " + x.getValue());
//		}
		
	}

	private void createPatient() {
		
		Patient newPat = BeanUtil.getBean(Patient.class);  
		
		  newPat.setName(requestParams.get("form:name"));
		  newPat.setEmail(requestParams.get("form:email"));
		  newPat.setPhoneNumber(Long.valueOf(requestParams.get("form:phonenumber")));
		  newPat.setMedicalId(Long.valueOf(requestParams.get("form:medicalId")));
		  newPat.setUsername(requestParams.get("form:username"));
		  newPat.setPassword(requestParams.get("form:password"));
		  newPat.setCounselor(counselorSelected.get(0));

		  pd.savePatient(newPat);
		  

		  patient = pd.selectAllPatient();
		  
		  System.err.println(newPat);
		
	}

	public void onCounselorSelect(SelectEvent event) {

		Employee selCoun = (Employee) event.getObject();

		if (!counselorSelected.contains(selCoun)) {
			counselorSelected.add(selCoun);
		}
	}

	public void onCounselorUnselect(UnselectEvent event) {
		Employee selCoun = (Employee) event.getObject();
		counselorSelected.remove(selCoun);
	}

	public void onManagerSelect(SelectEvent event) {

		Employee selMan = (Employee) event.getObject();

		if (!managerSelected.contains(selMan)) {
			managerSelected.add(selMan);
		}
	}

	public void onManagerUnselect(UnselectEvent event) {
		Employee selMan = (Employee) event.getObject();
		managerSelected.remove(selMan);
	}

	public void onPatientSelect(SelectEvent event) {

		Patient selPat = (Patient) event.getObject();

		if (!patientSelected.contains(selPat)) {
			patientSelected.add(selPat);
		}
	}

	public void onPatientUnselect(UnselectEvent event) {
		Patient selPat = (Patient) event.getObject();
		patientSelected.remove(selPat);
	}

	public List<UserType> getTypes() {
		return types;
	}

	public void setTypes(List<UserType> types) {
		this.types = types;
	}

	public Map<Bookings, BookingDetails> getBookingsMap() {
		return bookingsMap;
	}

	public void setBookingsMap(Map<Bookings, BookingDetails> bookingsMap) {
		this.bookingsMap = bookingsMap;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<Patient> getPatient() {
		return patient;
	}

	public void setPatient(List<Patient> patient) {
		this.patient = patient;
	}

	public List<Employee> getManagerSelected() {
		return managerSelected;
	}

	public void setManagerSelected(List<Employee> managerSelected) {
		this.managerSelected = managerSelected;
	}

	public List<Employee> getCounselorSelected() {
		return counselorSelected;
	}

	public void setCounselorSelected(List<Employee> counselorSelected) {
		this.counselorSelected = counselorSelected;
	}

	public List<Patient> getPatientSelected() {
		return patientSelected;
	}

	public void setPatientSelected(List<Patient> patientSelected) {
		this.patientSelected = patientSelected;
	}
	
	private void clearUp() {
		managerSelected.clear();
		counselorSelected.clear();
		patientSelected.clear();		
	}

}
package org.Backend;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.Backend.Converters.JacksonConverter;
import org.Backend.Entities.Employee;
import org.Backend.Entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;

@Path("msg")
public class JerseyWithSpringTrials {
	
	@PostConstruct
	private void setup() {
		setupEmp();
		setupEmpBoss();
		setupPat();
		setupPat2();
	}
	
	@Autowired
	private Employee emp;
	
	@Autowired
	private Employee empBoss;
	
	@Autowired
	private Patient pat;
	
	@Autowired
	private Patient pat2;
	
	
	private void setupEmp() {
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
	
	
	private void setupEmpBoss() {
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
	
	
	private void setupPat() {
		pat.setName("testPat");
		pat.setEmail("testPat@testPat.hu");
		pat.setUsername("testerPat");
		pat.setPassword("abc123");
		pat.setMedicalId(123l);
		pat.setCounselor(emp);
		pat.setPhoneNumber(123l);
	} 
	
	
	private void setupPat2() {
		pat2.setName("testPat2");
		pat2.setEmail("testPat2@testPat2.hu");
		pat2.setUsername("testerPat2");
		pat2.setPassword("abc1232");
		pat2.setMedicalId(1232l);
		pat2.setCounselor(empBoss);
		pat2.setPhoneNumber(123l);
	} 
	

	  @GET
	  @Path("/text")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String getMessage() throws JsonProcessingException {
	        
	        //return "My message\n" +JacksonConverter.pojoToJson(emp);
		  
		  return JacksonConverter.pojoToJson(emp);
	    }
	  
	  @GET
	  @Path("/json")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response getMessageAJ() throws JsonProcessingException {
	        
	        return Response.ok(JacksonConverter.pojoToJson(emp)).build();
	    }
	    
	
}

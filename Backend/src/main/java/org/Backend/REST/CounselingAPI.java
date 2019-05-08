package org.Backend.REST;

import static org.Backend.Converters.JacksonConverter.jsonToPojo;
import static org.Backend.Converters.JacksonConverter.pojoToJson;

import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.Backend.DAOs.PatientDao;
import org.Backend.Entities.Employee;
import org.Backend.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;

@Path("service")
public class CounselingAPI {
	
	@Autowired
	private EmployeeService empService;
	
	//temprarily for testing
	@Autowired
	private PatientDao patDao;
	  
	  @GET
	  @Path("/employee/{username}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response getEmployee(@PathParam("username") String usrnm) throws JsonProcessingException {
		  	
	        return Response.ok(pojoToJson(empService.employeeFinderByUsername(usrnm))).build();
	    }
	  
	  @GET
	  @Path("/user/{username}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response getPatient(@PathParam("username") String usrnm) throws JsonProcessingException {
		  	
	        return Response.ok(pojoToJson(patDao.selectPatientByUsername(usrnm).get(0))).build();
	    }
	  
	  
	  @POST
	  @Path("/employee/save")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response postSendBackEmployeeCompare(@HeaderParam("empJson") String empJson) throws IOException {
	        
		  Employee empGotBack = jsonToPojo(empJson, Employee.class);
		  Employee original = empService.employeeFinderByUsername(empGotBack.getUsername());		 
		  
		  return Response.ok(original.equals(empGotBack)).build();
	    }
	  
	  @POST
	  @Path("/employee/login")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response postLoginDemo(@FormParam("id")String id, @FormParam("pw")String pw, @FormParam("onoffswitch")String onoffswitch ) throws IOException {
 
		  
		  return Response.ok(id+" "+pw+" "+onoffswitch).build();
	    }
	  
	  
	  
}

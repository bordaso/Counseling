package org.Backend.REST;

import static org.Backend.Converters.JacksonConverter.jsonToPojo;
import static org.Backend.Converters.JacksonConverter.pojoToJson;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.Backend.JettyStart;
import org.Backend.DAOs.PatientDao;
import org.Backend.Entities.BookingDetails;
import org.Backend.Entities.Bookings;
import org.Backend.Entities.Employee;
import org.Backend.Enums.BookingResponse;
import org.Backend.Services.CommonService;
import org.Backend.Services.EmployeeService;
import org.Backend.Services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("service")
public class CounselingAPI {

	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private PatientService patService;
	
	@Autowired
	private CommonService comService;

	// temprarily for testing
	@Autowired
	private PatientDao patDao;
	
	
	@GET
	@Path("/shutdown")
	public void getShutDown() {	
		JettyStart.getServerInstance().shutdown();
	}


	@GET
	@Path("/logincheck")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLoginCheck() throws JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		int direction = logincheck(auth);
		
		String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString("LC" + direction);

		return Response.ok(jsonInString).build();
	}

	private int logincheck(Authentication auth) {

		if (auth != null) {

			if (auth instanceof AnonymousAuthenticationToken) {
				return 0;
			}

			if (auth.getAuthorities().iterator().next().getAuthority().equals("ROLE_EMPLOYEE")
					|| auth.getAuthorities().iterator().next().getAuthority().equals("ROLE_ADMIN")) {
				return 2;
			}

			return 1;
		}

		throw new NullPointerException();
	}

	@POST
	@Path("/all/bookings")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postSendBackBookings() throws IOException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String pudUser="";
		Object princ = auth.getPrincipal();
		UserDetails	princUD;
		StringBuilder returnVal=new StringBuilder();

		if(princ instanceof UserDetails) {
			princUD = (UserDetails)princ;			
		    pudUser = princUD.getUsername();	
		    
		    if(auth.getAuthorities().iterator().next().getAuthority().equals("ROLE_EMPLOYEE")
					|| auth.getAuthorities().iterator().next().getAuthority().equals("ROLE_ADMIN")) {
		    	
		    	 Map<Bookings, BookingDetails> empbdMap = empService.employeeFindAllUpcomingBookings(pudUser);
		    	 
		    	   for(Entry<Bookings, BookingDetails> entry : empbdMap.entrySet()) {
				    	returnVal.append(entry.getKey().toJson()+"#"+entry.getValue().toJson()+"##");		    	 
				    }
		    	   
		    	   System.err.println(returnVal);
		    	
		    	return Response.ok(returnVal).build(); 
			}		    
		    
		    Map<Bookings, BookingDetails> patbdMap =  patService.patientFindAllUpcomingBookings(pudUser);

		    for(Entry<Bookings, BookingDetails> entry : patbdMap.entrySet()) {
		    	returnVal.append(entry.getKey().toJson()+"#"+entry.getValue().toJson()+"##");		    	 
		    }
		    
		    System.err.println(returnVal);

		    return Response.ok(returnVal).build(); 
		}

		return Response.ok("{\"Custom_#_Error\"}").build();
	}
	
	@POST
	@Path("/all/bookingresponse")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postUpdateBookingResponse(@HeaderParam("bdid") String bdid, @HeaderParam("bdresponse") String bdresponse, @Context HttpServletRequest httpRequest) throws IOException {
		
		System.out.println(httpRequest);
		
		BookingResponse bdr=bdresponse.equals("1")?BookingResponse.ACCEPTED:BookingResponse.REJECTED;	

		boolean success = comService.updateBookingResponse(Long.parseLong(bdid), bdr);
				
		return Response.ok(pojoToJson(success)).build();
	}
	
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
	public Response postLoginDemo(@FormParam("id") String id, @FormParam("pw") String pw,
			@FormParam("onoffswitch") String onoffswitch) throws IOException {

		return Response.ok(id + " " + pw + " " + onoffswitch).build();
	}
		
	
}

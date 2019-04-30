package org.Backend.REST;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.Backend.Converters.JacksonConverter;
import org.Backend.Entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;

@Path("rest/service")
public class CounselingAPI {
	
	@Autowired
	private Employee emp;
	  
	  @GET
	  @Path("/json")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response getMessageAJ() throws JsonProcessingException {
	        
	        return Response.ok(JacksonConverter.pojoToJson(emp)).build();
	    }
}

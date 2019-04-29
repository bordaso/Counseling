package org.Backend.Services;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.Backend.Entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

@Path("msg")
public class JerseyMsg {
	
	@Autowired
	private Employee emp;

	  @GET
	  @Produces(MediaType.TEXT_PLAIN)
	  public String getMessage() {
	        
	        return "My message\n" +emp;
	    }
	  
	  
	  @GET
	  @Path("/json")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response getJson() {
	        Resource resource = new Resource();
	        resource.hello = "world";
	        return Response.ok(resource).build();
	    }

	    public static class Resource {
	        public String hello;
	    }

	    
	
}

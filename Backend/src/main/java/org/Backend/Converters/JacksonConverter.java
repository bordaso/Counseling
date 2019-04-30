package org.Backend.Converters;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JacksonConverter {
	
	private JacksonConverter() {		
	}

	public static <T> String pojoToJson(T var) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		// Java objects to JSON string - compact-print
		//String jsonString = mapper.writeValueAsString(emp);
		
		// Java objects to JSON file
		// mapper.writeValue(new File("/emp.json"), emp);
		// Java objects to JSON string - pretty-print
		String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(var);
		
		//System.err.println(jsonInString);

		return jsonInString;
	}

	public static <T> T jsonToPojo(String jsonInString, Class<T> in) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		// JSON file to Java object
		// Employee emp = mapper.readValue(new File("c:\\test\\emp.json"), Employee.class);
		T entity = mapper.readValue(jsonInString, in);

		return entity;
	}

}

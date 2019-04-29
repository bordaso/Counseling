package org.Backend;

import java.io.IOException;

import org.Backend.Entities.Employee;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JacksonEmployeeConverter {

	public String employeePojoToJson(Employee emp) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		
		// Java objects to JSON file
		// mapper.writeValue(new File("c:\\test\\emp.json"), emp);

		// Java objects to JSON string - compact-print
		String jsonString = mapper.writeValueAsString(emp);

		System.out.println(jsonString);

		// Java objects to JSON string - pretty-print
		String jsonInString2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(emp);

		System.err.println(jsonInString2);

		return jsonString;

	}

	public Employee jsonToEmployeePojo(String jsonInString) throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		// Employee emp = mapper.readValue(new File("c:\\test\\emp.json"),
		// Employee.class);

		Employee emp = mapper.readValue(jsonInString, Employee.class);

		// compact print
		System.out.println(emp);

		// pretty print
		String emp2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(emp);

		System.out.println(emp2);

		return emp;

	}

}

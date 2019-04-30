package org.Backend.Converters;

import java.io.IOException;

import org.Backend.Entities.Employee;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JacksonEmployeeConverter {

	public String employeePojoToJson(Employee emp) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(emp);

		return jsonInString;
	}

	public Employee jsonToEmployeePojo(String jsonInString) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		Employee emp = mapper.readValue(jsonInString, Employee.class);

		return emp;
	}

}

package org.Backend.Converters;

import java.io.IOException;

import org.Backend.Entities.Patient;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JacksonPatientConverter {

	public String patientPojoToJson(Patient pat) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pat);

		return jsonInString;

	}

	public Patient jsonToPatientPojo(String jsonInString) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		Patient pat = mapper.readValue(jsonInString, Patient.class);
		
		return pat;

	}


}
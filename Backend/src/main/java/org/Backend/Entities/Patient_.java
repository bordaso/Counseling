package org.Backend.Entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Patient.class)
public abstract class Patient_ extends org.Backend.Entities.User_ {

	public static volatile SingularAttribute<Patient, Employee> counselor;
	public static volatile SingularAttribute<Patient, Long> medicalId;
	public static volatile SingularAttribute<Patient, Long> version;
	public static volatile SetAttribute<Patient, BookingDetails> bookingDetails;

	public static final String COUNSELOR = "counselor";
	public static final String MEDICAL_ID = "medicalId";
	public static final String VERSION = "version";
	public static final String BOOKING_DETAILS = "bookingDetails";

}


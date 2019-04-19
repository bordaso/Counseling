package org.Backend;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Employee.class)
public abstract class Employee_ extends org.Backend.User_ {

	public static volatile SingularAttribute<Employee, String> personalId;
	public static volatile ListAttribute<Employee, Patient> patientList;
	public static volatile SingularAttribute<Employee, Employee> reportsTo;
	public static volatile SingularAttribute<Employee, Long> id;
	public static volatile SingularAttribute<Employee, Long> version;
	public static volatile SetAttribute<Employee, BookingDetails> bookingDetails;

	public static final String PERSONAL_ID = "personalId";
	public static final String PATIENT_LIST = "patientList";
	public static final String REPORTS_TO = "reportsTo";
	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String BOOKING_DETAILS = "bookingDetails";

}


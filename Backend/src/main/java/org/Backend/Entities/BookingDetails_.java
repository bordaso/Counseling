package org.Backend.Entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import org.Backend.Enums.BookingResponse;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BookingDetails.class)
public abstract class BookingDetails_ {

	public static volatile SingularAttribute<BookingDetails, Bookings> booking;
	public static volatile SingularAttribute<BookingDetails, Patient> patient;
	public static volatile SingularAttribute<BookingDetails, BookingResponse> response;
	public static volatile SingularAttribute<BookingDetails, Long> id;
	public static volatile SingularAttribute<BookingDetails, Employee> employee;
	public static volatile SingularAttribute<BookingDetails, Long> version;

	public static final String BOOKING = "booking";
	public static final String PATIENT = "patient";
	public static final String RESPONSE = "response";
	public static final String ID = "id";
	public static final String EMPLOYEE = "employee";
	public static final String VERSION = "version";

}


package org.Backend;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Bookings.class)
public abstract class Bookings_ {

	public static volatile SingularAttribute<Bookings, Boolean> archived;
	public static volatile SingularAttribute<Bookings, byte[]> report;
	public static volatile SingularAttribute<Bookings, LocalDateTime> start;
	public static volatile SingularAttribute<Bookings, LocalDateTime> end;
	public static volatile SingularAttribute<Bookings, NotificationSetup> notificationId;
	public static volatile SingularAttribute<Bookings, Long> id;
	public static volatile SingularAttribute<Bookings, String> title;
	public static volatile SingularAttribute<Bookings, Long> version;
	public static volatile SingularAttribute<Bookings, String> room;
	public static volatile SetAttribute<Bookings, BookingDetails> bookingDetails;

	public static final String ARCHIVED = "archived";
	public static final String REPORT = "report";
	public static final String START = "start";
	public static final String END = "end";
	public static final String NOTIFICATION_ID = "notificationId";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String VERSION = "version";
	public static final String ROOM = "room";
	public static final String BOOKING_DETAILS = "bookingDetails";

}


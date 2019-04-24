package org.Backend.Entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NotificationSetup.class)
public abstract class NotificationSetup_ {

	public static volatile SingularAttribute<NotificationSetup, Boolean> sms;
	public static volatile SingularAttribute<NotificationSetup, Long> id;
	public static volatile SingularAttribute<NotificationSetup, Long> recurringTime;
	public static volatile SingularAttribute<NotificationSetup, Bookings> boookingNotifId;
	public static volatile SingularAttribute<NotificationSetup, Long> version;
	public static volatile SingularAttribute<NotificationSetup, String> content;
	public static volatile SingularAttribute<NotificationSetup, Boolean> email;

	public static final String SMS = "sms";
	public static final String ID = "id";
	public static final String RECURRING_TIME = "recurringTime";
	public static final String BOOOKING_NOTIF_ID = "boookingNotifId";
	public static final String VERSION = "version";
	public static final String CONTENT = "content";
	public static final String EMAIL = "email";

}


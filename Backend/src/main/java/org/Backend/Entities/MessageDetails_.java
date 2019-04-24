package org.Backend.Entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MessageDetails.class)
public abstract class MessageDetails_ {

	public static volatile SingularAttribute<MessageDetails, Boolean> archived;
	public static volatile SingularAttribute<MessageDetails, Boolean> read;
	public static volatile SingularAttribute<MessageDetails, User> receiver;
	public static volatile SingularAttribute<MessageDetails, User> sender;
	public static volatile SingularAttribute<MessageDetails, Message> msgId;
	public static volatile SingularAttribute<MessageDetails, Long> id;
	public static volatile SingularAttribute<MessageDetails, Long> version;

	public static final String ARCHIVED = "archived";
	public static final String READ = "read";
	public static final String RECEIVER = "receiver";
	public static final String SENDER = "sender";
	public static final String MSG_ID = "msgId";
	public static final String ID = "id";
	public static final String VERSION = "version";

}


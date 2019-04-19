package org.Backend;

import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Message.class)
public abstract class Message_ {

	public static volatile SingularAttribute<Message, byte[]> attachment;
	public static volatile SetAttribute<Message, MessageDetails> messageDetails;
	public static volatile SingularAttribute<Message, Long> replyToId;
	public static volatile SingularAttribute<Message, Long> id;
	public static volatile SingularAttribute<Message, LocalDateTime> sentAt;
	public static volatile SingularAttribute<Message, Long> version;
	public static volatile SingularAttribute<Message, String> content;

	public static final String ATTACHMENT = "attachment";
	public static final String MESSAGE_DETAILS = "messageDetails";
	public static final String REPLY_TO_ID = "replyToId";
	public static final String ID = "id";
	public static final String SENT_AT = "sentAt";
	public static final String VERSION = "version";
	public static final String CONTENT = "content";

}


package org.Backend.DAOs;

import java.util.List;

import org.Backend.Entities.Message;
import org.Backend.Entities.User;

public interface MessageDao {

	void saveMessage(Message input);
	
	void updateMessage(Message toBeUpdated);
	
	void deleteMessage(Long id);
	
	List<Message> selectAllMessage();	
	
	Message selectMessageById(Long id);
	
	List<Message> selectMessageByReplyToId(Long replyToId);
	
	List<Message> selectMessageBySender(User Sender);

	void clearMessage();	

	void setMyProxy(MessageDao proxy);

}

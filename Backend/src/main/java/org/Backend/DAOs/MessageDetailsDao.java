package org.Backend.DAOs;

import java.util.List;

import org.Backend.Entities.Message;
import org.Backend.Entities.MessageDetails;
import org.Backend.Entities.User;

public interface MessageDetailsDao {

	void saveMessageDetails(MessageDetails input);
	
	void updateMessageDetails(MessageDetails toBeUpdated);
	
	void deleteMessageDetailsById(Long id);
	
	void deleteMessageDetailsByMsgId(Message msgId);
	
	List<MessageDetails> selectAllMessageDetails();	
	
	List<MessageDetails> selectMessageDetailsByMsgId(Message msgId);
	
	List<MessageDetails> selectMessageeDetailsByReceiver(User receiver);
	
	MessageDetails selectMessageDetailsById(Long id);

	void clearMessageDetails();	

	void setMyProxy(MessageDetailsDao proxy);

}

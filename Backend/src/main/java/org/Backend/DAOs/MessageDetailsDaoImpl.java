package org.Backend.DAOs;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.Backend.Entities.Message;
import org.Backend.Entities.MessageDetails;
import org.Backend.Entities.MessageDetails_;
import org.Backend.Entities.Message_;
import org.Backend.Entities.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MessageDetailsDaoImpl implements MessageDetailsDao {

	@Autowired
	protected SessionFactory sessionFactory;

	protected CriteriaBuilder cb;

	private MessageDetailsDao proxy;

	@PostConstruct
	public void createCriteriaBuilder() {
		cb = sessionFactory.getCriteriaBuilder();
	}

	@Override
	public void setMyProxy(MessageDetailsDao proxy) {
		this.proxy = proxy;
	}
	
	@Transactional
	@Override
	public void saveMessageDetails(MessageDetails input) {
		sessionFactory.getCurrentSession().save(input);
	}

	@Transactional
	@Override
	public void updateMessageDetails(MessageDetails toBeUpdated) {
		sessionFactory.getCurrentSession().update(toBeUpdated);
	}

	@Transactional
	@Override
	public void deleteMessageDetailsById(Long id) {
		// ONLY UPDATE ARCHIVED
		CriteriaUpdate<MessageDetails> update = cb.createCriteriaUpdate(MessageDetails.class);
		Root<MessageDetails> root = update.from(MessageDetails.class);
		update.set(root.get(MessageDetails_.archived), true).where(cb.equal(root.get(MessageDetails_.id), id));
	
		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
		
	}

	@Transactional
	@Override
	public void deleteMessageDetailsByMsgId(Message msgId) {
		// ONLY UPDATE ARCHIVED
		CriteriaUpdate<MessageDetails> update = cb.createCriteriaUpdate(MessageDetails.class);
		Root<MessageDetails> root = update.from(MessageDetails.class);
		update.set(root.get(MessageDetails_.archived), true).where(cb.equal(root.get(MessageDetails_.msgId), msgId));
	
		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public List<MessageDetails> selectAllMessageDetails() {
		CriteriaQuery<MessageDetails> query = cb.createQuery(MessageDetails.class);
		query.from(MessageDetails.class);
		TypedQuery<MessageDetails> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public MessageDetails selectMessageDetailsById(Long id) {
		CriteriaQuery<MessageDetails> query = cb.createQuery(MessageDetails.class);
		Root<MessageDetails> root = query.from(MessageDetails.class);
		query.where(cb.equal(root.get(MessageDetails_.id), id));
		TypedQuery<MessageDetails> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);
		List<MessageDetails> resultList = queryExecuted.getResultList();
		
		return resultList.isEmpty()?null:resultList.get(0);
	}
	
	@Transactional
	@Override
	public List<MessageDetails> selectMessageDetailsByMsgId(Message msgId) {
		CriteriaQuery<MessageDetails> query = cb.createQuery(MessageDetails.class);
		Root<MessageDetails> root = query.from(MessageDetails.class);
		query.where(cb.equal(root.get(MessageDetails_.id), msgId));
		TypedQuery<MessageDetails> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);
		
		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public List<MessageDetails> selectMessageeDetailsByReceiver(User receiver) {
		CriteriaQuery<MessageDetails> query = cb.createQuery(MessageDetails.class);
		Root<MessageDetails> root = query.from(MessageDetails.class);
		query.where(cb.equal(root.get(MessageDetails_.receiver), receiver));
		TypedQuery<MessageDetails> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);
		
		return queryExecuted.getResultList();
	}

	
	@Transactional
	@Override
	public void clearMessageDetails() {
		CriteriaDelete<MessageDetails> query = cb.createCriteriaDelete(MessageDetails.class);
		query.from(MessageDetails.class);
		sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
	}
	
}


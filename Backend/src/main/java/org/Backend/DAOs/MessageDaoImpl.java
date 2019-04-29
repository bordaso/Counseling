package org.Backend.DAOs;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.Backend.Entities.Message;
import org.Backend.Entities.Message_;
import org.Backend.Entities.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDaoImpl implements MessageDao {

	@Autowired
	protected SessionFactory sessionFactory;

	protected CriteriaBuilder cb;

	private MessageDao proxy;

	@PostConstruct
	public void createCriteriaBuilder() {
		cb = sessionFactory.getCriteriaBuilder();
	}

	@Override
	public void setMyProxy(MessageDao proxy) {
		this.proxy = proxy;
	}

	@Transactional
	@Override
	public void saveMessage(Message input) {
		sessionFactory.getCurrentSession().save(input);
	}

	@Transactional
	@Override
	public void updateMessage(Message toBeUpdated) {
		sessionFactory.getCurrentSession().update(toBeUpdated);
	}

	@Transactional
	@Override
	public void deleteMessage(Long id) {
		String hql = "delete from Message where id= :id";
		sessionFactory.getCurrentSession().createQuery(hql).setParameter("id", id).executeUpdate();	
	}

	@Transactional
	@Override
	public List<Message> selectAllMessage() {
		CriteriaQuery<Message> query = cb.createQuery(Message.class);
		query.from(Message.class);
		TypedQuery<Message> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public Message selectMessageById(Long id) {
		CriteriaQuery<Message> query = cb.createQuery(Message.class);
		Root<Message> root = query.from(Message.class);
		query.where(cb.equal(root.get(Message_.id), id));
		TypedQuery<Message> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);
		List<Message> resultList = queryExecuted.getResultList();
		
		return resultList.isEmpty()?null:resultList.get(0);
	}

	@Transactional
	@Override
	public Message selectMessageByReplyToId(Long replyToId) {
		CriteriaQuery<Message> query = cb.createQuery(Message.class);
		Root<Message> root = query.from(Message.class);
		query.where(cb.equal(root.get(Message_.replyToId), replyToId));
		TypedQuery<Message> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);
		List<Message> resultList = queryExecuted.getResultList();
		
		return resultList.isEmpty()?null:resultList.get(0);
	}

	@Transactional
	@Override
	public List<Message> selectMessageBySender(User sender) {
		CriteriaQuery<Message> query = cb.createQuery(Message.class);
		Root<Message> root = query.from(Message.class);
		query.where(cb.equal(root.get(Message_.sender), sender));
		TypedQuery<Message> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public void clearMessage() {
		CriteriaDelete<Message> query = cb.createCriteriaDelete(Message.class);
		query.from(Message.class);
		sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
	}

}


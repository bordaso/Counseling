package org.Backend.DAOs;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.Backend.Entities.BookingDetails;
import org.Backend.Entities.BookingDetails_;
import org.Backend.Entities.Bookings;
import org.Backend.Entities.NotificationSetup;
import org.Backend.Entities.NotificationSetup_;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class NotificationSetupDaoImpl implements NotificationSetupDao {

	@Autowired
	protected SessionFactory sessionFactory;

	protected CriteriaBuilder cb;

	private NotificationSetupDao proxy;

	@PostConstruct
	public void createCriteriaBuilder() {
		cb = sessionFactory.getCriteriaBuilder();
	}

	@Transactional
	@Override
	public void setMyProxy(NotificationSetupDao proxy) {
		this.proxy = proxy;
	}

	@Transactional
	@Override
	public void saveNotificationSetup(NotificationSetup input) {
		sessionFactory.getCurrentSession().save(input);
	}

	@Transactional
	@Override
	public void updateNotificationSetup(NotificationSetup toBeUpdated) {
		sessionFactory.getCurrentSession().update(toBeUpdated);
	}

	@Transactional
	@Override
	public void deleteNotificationSetup(Long id) {
		String hql = "delete from NotificationSetup where id= :id";
		sessionFactory.getCurrentSession().createQuery(hql).setParameter("id", id).executeUpdate();
	}

	@Transactional
	@Override
	public List<NotificationSetup> selectAllNotificationSetup() {
		CriteriaQuery<NotificationSetup> query = cb.createQuery(NotificationSetup.class);
		query.from(NotificationSetup.class);
		TypedQuery<NotificationSetup> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public NotificationSetup selectNotificationSetupById(Long id) {
		CriteriaQuery<NotificationSetup> query = cb.createQuery(NotificationSetup.class);
		Root<NotificationSetup> root = query.from(NotificationSetup.class);
		query.where(cb.equal(root.get(NotificationSetup_.id), id));
		TypedQuery<NotificationSetup> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList().get(0);
	}

	@Transactional
	@Override
	public NotificationSetup selectNotificationSetupByBooking(Bookings booking) {
		CriteriaQuery<NotificationSetup> query = cb.createQuery(NotificationSetup.class);
		Root<NotificationSetup> root = query.from(NotificationSetup.class);
		query.where(cb.equal(root.get(NotificationSetup_.boookingNotifId), booking));
		TypedQuery<NotificationSetup> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList().get(0);
	}

	@Transactional
	@Override
	public void updateNotificationSetupBookings(Long id, Bookings booking) {
		CriteriaUpdate<NotificationSetup> update = cb.createCriteriaUpdate(NotificationSetup.class);
		Root<NotificationSetup> root = update.from(NotificationSetup.class);
		update.set(root.get(NotificationSetup_.boookingNotifId), booking)
				.where(cb.equal(root.get(NotificationSetup_.id), id));

		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void updateNotificationSetupRecurringTime(Long id, long newTime) {
		CriteriaUpdate<NotificationSetup> update = cb.createCriteriaUpdate(NotificationSetup.class);
		Root<NotificationSetup> root = update.from(NotificationSetup.class);
		update.set(root.get(NotificationSetup_.recurringTime), newTime)
				.where(cb.equal(root.get(NotificationSetup_.id), id));

		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void updateNotificationSetupContent(Long id, String content) {
		CriteriaUpdate<NotificationSetup> update = cb.createCriteriaUpdate(NotificationSetup.class);
		Root<NotificationSetup> root = update.from(NotificationSetup.class);
		update.set(root.get(NotificationSetup_.content), content).where(cb.equal(root.get(NotificationSetup_.id), id));

		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void updateNotificationSetupEmail(Long id, boolean newValue) {
		CriteriaUpdate<NotificationSetup> update = cb.createCriteriaUpdate(NotificationSetup.class);
		Root<NotificationSetup> root = update.from(NotificationSetup.class);
		update.set(root.get(NotificationSetup_.email), newValue).where(cb.equal(root.get(NotificationSetup_.id), id));

		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void updateNotificationSetupSms(Long id, boolean newValue) {
		CriteriaUpdate<NotificationSetup> update = cb.createCriteriaUpdate(NotificationSetup.class);
		Root<NotificationSetup> root = update.from(NotificationSetup.class);
		update.set(root.get(NotificationSetup_.sms), newValue).where(cb.equal(root.get(NotificationSetup_.id), id));

		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void clearNotificationSetup() {
		CriteriaDelete<NotificationSetup> query = cb.createCriteriaDelete(NotificationSetup.class);
		query.from(NotificationSetup.class);
		sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
	}

}

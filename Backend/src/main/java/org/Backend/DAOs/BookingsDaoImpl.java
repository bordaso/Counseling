package org.Backend.DAOs;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.Backend.Entities.Bookings;
import org.Backend.Entities.Bookings_;
import org.Backend.Entities.Patient;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BookingsDaoImpl implements BookingsDao {

	@Autowired
	protected SessionFactory sessionFactory;

	protected CriteriaBuilder cb;

	private BookingsDao proxy;

	@PostConstruct
	public void createCriteriaBuilder() {
		cb = sessionFactory.getCriteriaBuilder();
	}

	@Transactional
	@Override
	public void setMyProxy(BookingsDao proxy) {
		this.proxy = proxy;
	}

	@Transactional
	@Override
	public void saveBooking(Bookings input) {
		sessionFactory.getCurrentSession().save(input);
	}

	@Transactional
	@Override
	public void updateBooking(Bookings toBeUpdated) {
		sessionFactory.getCurrentSession().update(toBeUpdated);
	}

	@Transactional
	@Override
	public void deleteBooking(Long id) {
		String hql = "delete from Bookings where id= :id";
		sessionFactory.getCurrentSession().createQuery(hql).setParameter("id", id).executeUpdate();
	}

	@Transactional
	@Override
	public List<Bookings> selectAllBooking() {
		CriteriaQuery<Bookings> query = cb.createQuery(Bookings.class);
		query.from(Bookings.class);
		TypedQuery<Bookings> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public Bookings selectBookingById(Long id) {
		CriteriaQuery<Bookings> query = cb.createQuery(Bookings.class);
		Root<Bookings> root = query.from(Bookings.class);
		query.where(cb.equal(root.get(Bookings_.id), id));
		TypedQuery<Bookings> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList().get(0);
	}

	@Transactional
	@Override
	public List<Bookings> selectBookingByTitle(String inputTitle) {
		CriteriaQuery<Bookings> query = cb.createQuery(Bookings.class);
		Root<Bookings> root = query.from(Bookings.class);
		query.where(cb.equal(root.get(Bookings_.title), inputTitle));
		TypedQuery<Bookings> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public void updateBookingTitle(Long id, String newTitle) {
		CriteriaUpdate<Bookings> update = cb.createCriteriaUpdate(Bookings.class);
		Root<Bookings> root = update.from(Bookings.class);
		update.set(root.get(Bookings_.title), newTitle).where(cb.equal(root.get(Bookings_.id), id));

		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void updateBookingReport(Long id, byte[] newReport) {
		CriteriaUpdate<Bookings> update = cb.createCriteriaUpdate(Bookings.class);
		Root<Bookings> root = update.from(Bookings.class);
		update.set(root.get(Bookings_.report), newReport).where(cb.equal(root.get(Bookings_.id), id));

		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void updateBookingStart(Long id, LocalDateTime newStart) {
		CriteriaUpdate<Bookings> update = cb.createCriteriaUpdate(Bookings.class);
		Root<Bookings> root = update.from(Bookings.class);
		update.set(root.get(Bookings_.start), newStart).where(cb.equal(root.get(Bookings_.id), id));

		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void updateBookingEnd(Long id, LocalDateTime newEnd) {
		CriteriaUpdate<Bookings> update = cb.createCriteriaUpdate(Bookings.class);
		Root<Bookings> root = update.from(Bookings.class);
		update.set(root.get(Bookings_.end), newEnd).where(cb.equal(root.get(Bookings_.id), id));

		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void updateBookingRoom(Long id, String newRoom) {
		CriteriaUpdate<Bookings> update = cb.createCriteriaUpdate(Bookings.class);
		Root<Bookings> root = update.from(Bookings.class);
		update.set(root.get(Bookings_.room), newRoom).where(cb.equal(root.get(Bookings_.id), id));

		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void updateBookingArchived(Long id, boolean newValue) {
		CriteriaUpdate<Bookings> update = cb.createCriteriaUpdate(Bookings.class);
		Root<Bookings> root = update.from(Bookings.class);
		update.set(root.get(Bookings_.archived), newValue).where(cb.equal(root.get(Bookings_.id), id));

		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void clearBookings() {
		CriteriaDelete<Bookings> query = cb.createCriteriaDelete(Bookings.class);
		query.from(Bookings.class);
		sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
	}
}

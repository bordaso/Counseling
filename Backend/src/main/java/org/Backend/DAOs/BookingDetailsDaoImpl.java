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
import org.Backend.Entities.Bookings_;
import org.Backend.Entities.Employee;
import org.Backend.Entities.Patient;
import org.Backend.Enums.BookingResponse;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BookingDetailsDaoImpl implements BookingDetailsDao {

	@Autowired
	protected SessionFactory sessionFactory;

	protected CriteriaBuilder cb;

	private BookingDetailsDao proxy;

	@PostConstruct
	public void createCriteriaBuilder() {
		cb = sessionFactory.getCriteriaBuilder();
	}

	@Transactional
	@Override
	public void setMyProxy(BookingDetailsDao proxy) {
		this.proxy = proxy;
	}

	@Transactional
	@Override
	public void saveBookingDetails(BookingDetails input) {
		sessionFactory.getCurrentSession().save(input);
	}

	@Transactional
	@Override
	public void updateBookingDetails(BookingDetails toBeUpdated) {
		sessionFactory.getCurrentSession().update(toBeUpdated);

	}

	@Transactional
	@Override
	public void deleteBookingDetails(Long id) {
		String hql = "delete from BookingDetails where id= :id";
		sessionFactory.getCurrentSession().createQuery(hql).setParameter("id", id).executeUpdate();

	}

	@Transactional
	@Override
	public List<BookingDetails> selectAllBookingDetails() {
		CriteriaQuery<BookingDetails> query = cb.createQuery(BookingDetails.class);
		query.from(BookingDetails.class);
		TypedQuery<BookingDetails> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public BookingDetails selectBookingDetailsById(Long id) {
		CriteriaQuery<BookingDetails> query = cb.createQuery(BookingDetails.class);
		Root<BookingDetails> root = query.from(BookingDetails.class);
		query.where(cb.equal(root.get(BookingDetails_.id), id));
		TypedQuery<BookingDetails> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);
		List<BookingDetails> resultList = queryExecuted.getResultList();
		
		return resultList.isEmpty()?null:resultList.get(0);
	}

	@Transactional
	@Override
	public List<BookingDetails> selectBookingDetailsByBooking(Bookings booking) {
		CriteriaQuery<BookingDetails> query = cb.createQuery(BookingDetails.class);
		Root<BookingDetails> root = query.from(BookingDetails.class);
		query.where(cb.equal(root.get(BookingDetails_.booking), booking));
		TypedQuery<BookingDetails> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public List<BookingDetails> selectBookingDetailsByEmployee(Employee employee) {
		CriteriaQuery<BookingDetails> query = cb.createQuery(BookingDetails.class);		
		Root<BookingDetails> root = query.from(BookingDetails.class);
		query.where(cb.equal(root.get(BookingDetails_.employee), employee));
		TypedQuery<BookingDetails> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		//.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); ??
		
		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public List<BookingDetails> selectBookingDetailsByPatient(Patient patient) {
		CriteriaQuery<BookingDetails> query = cb.createQuery(BookingDetails.class);
		Root<BookingDetails> root = query.from(BookingDetails.class);
		query.where(cb.equal(root.get(BookingDetails_.patient), patient));
		TypedQuery<BookingDetails> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public void updateBookingDetailsBookings(Long id, Bookings booking) {
		CriteriaUpdate<BookingDetails> update = cb.createCriteriaUpdate(BookingDetails.class);
		Root<BookingDetails> root = update.from(BookingDetails.class);
		update.set(root.get(BookingDetails_.booking), booking).where(cb.equal(root.get(BookingDetails_.id), id));

		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void updateBookingDetailsEmployee(Long id, Employee employee) {
		CriteriaUpdate<BookingDetails> update = cb.createCriteriaUpdate(BookingDetails.class);
		Root<BookingDetails> root = update.from(BookingDetails.class);
		update.set(root.get(BookingDetails_.employee), employee).where(cb.equal(root.get(BookingDetails_.id), id));

		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void updateBookingDetailsPatient(Long id, Patient patient) {
		CriteriaUpdate<BookingDetails> update = cb.createCriteriaUpdate(BookingDetails.class);
		Root<BookingDetails> root = update.from(BookingDetails.class);
		update.set(root.get(BookingDetails_.patient), patient).where(cb.equal(root.get(BookingDetails_.id), id));

		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void updateBookingDetailsResponse(Long id, BookingResponse newValue) {
		CriteriaUpdate<BookingDetails> update = cb.createCriteriaUpdate(BookingDetails.class);
		Root<BookingDetails> root = update.from(BookingDetails.class);
		update.set(root.get(BookingDetails_.response), newValue).where(cb.equal(root.get(BookingDetails_.id), id));

		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void clearBookingDetails() {
		CriteriaDelete<BookingDetails> query = cb.createCriteriaDelete(BookingDetails.class);
		query.from(BookingDetails.class);
		sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
	}
}

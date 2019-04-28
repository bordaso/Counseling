package org.Backend.DAOs;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.Backend.Entities.Employee;
import org.Backend.Entities.Patient;
import org.Backend.Entities.Patient_;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PatientDaoImpl implements PatientDao {

	@Autowired
	protected SessionFactory sessionFactory;

	protected CriteriaBuilder cb;

	private PatientDao proxy;

	@PostConstruct
	public void createCriteriaBuilder() {
		cb = sessionFactory.getCriteriaBuilder();
	}

	@Override
	public void setMyProxy(PatientDao proxy) {
		this.proxy = proxy;
	}

	@Transactional
	@Override
	public void savePatient(Patient input) {
		System.err.println("START************PatientDaoImpl");
		sessionFactory.getCurrentSession().save(input);
		System.err.println("END************PatientDaoImpl");
	}
	
	@Transactional
	@Override
	public void updatePatientCounselor(Long id, Employee counselor) {
		CriteriaUpdate<Patient> update = cb.createCriteriaUpdate(Patient.class);
		Root<Patient> root = update.from(Patient.class);
		update.set(root.get(Patient_.counselor), counselor).where(cb.equal(root.get(Patient_.id), id));
	
		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void updatePatientPhone(Long id, Long newPhone) {
		CriteriaUpdate<Patient> update = cb.createCriteriaUpdate(Patient.class);
		Root<Patient> root = update.from(Patient.class);
		update.set(root.get(Patient_.phoneNumber), newPhone).where(cb.equal(root.get(Patient_.id), id));
		
		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}
	
	@Transactional
	@Override
	public void updatePatient(Patient toBeUpdated) {
		sessionFactory.getCurrentSession().update(toBeUpdated);
	}
	
	@Transactional
	@Override
	public void updatePatientName(String oldName, String newName) {
		CriteriaUpdate<Patient> update = cb.createCriteriaUpdate(Patient.class);
		Root<Patient> root = update.from(Patient.class);
		update.set(root.get(Patient_.name), newName).where(cb.equal(root.get(Patient_.name), oldName));
		
		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}
	
	@Transactional
	@Override
	public void updatePatientNameWithHQL(String oldName, String newName, Patient emp) {		
		String hql = "update from Patient set name= :newName where name= :oldName";
		sessionFactory.getCurrentSession().createQuery(hql).setParameter("oldName", oldName).setParameter("newName", newName).executeUpdate();			
			
	}
	
	@Transactional
	@Override
	public List<Patient> selectAllPatient() {
		CriteriaQuery<Patient> query = cb.createQuery(Patient.class);
		query.from(Patient.class);
		TypedQuery<Patient> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public List<Patient> selectPatientByName(String inputName) {
		CriteriaQuery<Patient> query = cb.createQuery(Patient.class);
		Root<Patient> root = query.from(Patient.class);
		query.where(cb.equal(root.get(Patient_.name), inputName));
		TypedQuery<Patient> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public List<Patient> selectPatientById(Long id) {
		CriteriaQuery<Patient> query = cb.createQuery(Patient.class);
		Root<Patient> root = query.from(Patient.class);
		query.where(cb.equal(root.get(Patient_.id), id));
		TypedQuery<Patient> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public void deletePatient(Long id) {
		String hql = "delete from Patient where id= :id";
		sessionFactory.getCurrentSession().createQuery(hql).setParameter("id", id).executeUpdate();		
	}

	@Transactional
	@Override
	public void clearPatient() {
		CriteriaDelete<Patient> query = cb.createCriteriaDelete(Patient.class);
		query.from(Patient.class);
		sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
	}

}


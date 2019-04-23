package org.Backend;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	@Autowired
	protected SessionFactory sessionFactory;

	protected CriteriaBuilder cb;

	private EmployeeDao proxy;

	@PostConstruct
	public void createCriteriaBuilder() {
		cb = sessionFactory.getCriteriaBuilder();
	}

	@Override
	public void setMyProxy(EmployeeDao proxy) {
		this.proxy = proxy;
	}

	@Transactional
	@Override
	public void saveEmployee(Employee input) {
		sessionFactory.getCurrentSession().save(input);
	}
	
	@Transactional
	@Override
	public void updateEmployeeReportsTo(Long id, Employee newBoss) {
		CriteriaUpdate<Employee> update = cb.createCriteriaUpdate(Employee.class);
		Root<Employee> root = update.from(Employee.class);
		update.set(root.get(Employee_.reportsTo), newBoss).where(cb.equal(root.get(Employee_.id), id));
		
		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}

	@Transactional
	@Override
	public void updateEmployeePhone(Long id, Long newPhone) {
		CriteriaUpdate<Employee> update = cb.createCriteriaUpdate(Employee.class);
		Root<Employee> root = update.from(Employee.class);
		update.set(root.get(Employee_.phoneNumber), newPhone).where(cb.equal(root.get(Employee_.id), id));
		
		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}
	
	
	@Transactional
	@Override
	public void updateEmployeeName(String oldName, String newName) {
		CriteriaUpdate<Employee> update = cb.createCriteriaUpdate(Employee.class);
		Root<Employee> root = update.from(Employee.class);
		update.set(root.get(Employee_.name), newName).where(cb.equal(root.get(Employee_.name), oldName));
		
		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}
	
	@Transactional
	@Override
	public void updateEmployeeNameWithHQL(String oldName, String newName, Employee emp) {		
		String hql = "update from Employee set name= :newName where name= :oldName";
		sessionFactory.getCurrentSession().createQuery(hql).setParameter("oldName", oldName).setParameter("newName", newName).executeUpdate();			
			
	}
	
	@Transactional
	@Override
	public List<Employee> selectAllEmployee() {
		CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
		Root<Employee> root = query.from(Employee.class);
		TypedQuery<Employee> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public List<Employee> selectEmployeeByName(String inputName) {
		CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
		Root<Employee> root = query.from(Employee.class);
		query.where(cb.equal(root.get(Employee_.name), inputName));
		TypedQuery<Employee> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public List<Employee> selectEmployeeById(Long id) {
		CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
		Root<Employee> root = query.from(Employee.class);
		query.where(cb.equal(root.get(Employee_.id), id));
		TypedQuery<Employee> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);

		return queryExecuted.getResultList();
	}

	@Transactional
	@Override
	public void deleteEmployee(Long id) {
		String hql = "delete from Employee where id= :id";
		sessionFactory.getCurrentSession().createQuery(hql).setParameter("id", id).executeUpdate();		
	}

	@Transactional
	@Override
	public void clearEmployee() {
		CriteriaDelete<Employee> query = cb.createCriteriaDelete(Employee.class);
		query.from(Employee.class);
		sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
	}

}

// public <T extends PostModerate> int flagSpam(
// EntityManager entityManager,
// Class<T> postModerateClass) {
//
// CriteriaBuilder builder = entityManager
// .getCriteriaBuilder();
//
// CriteriaUpdate<T> update = builder
// .createCriteriaUpdate(postModerateClass);
//
// Root<T> root = update.from(postModerateClass);
//
// Expression<Boolean> filterPredicate = builder
// .like(
// builder.lower(root.get("message")),
// "%spam%"
// );
//
// if(Post.class.isAssignableFrom(postModerateClass)) {
// filterPredicate = builder.or(
// filterPredicate, builder
// .like(
// builder.lower(root.get("title")),
// "%spam%"
// )
// );
// }
//
// update
// .set(root.get("status"), PostStatus.SPAM)
// .set(root.get("updatedOn"), new Date())
// .where(filterPredicate);
//
// return entityManager
// .createQuery(update)
// .executeUpdate();
// }

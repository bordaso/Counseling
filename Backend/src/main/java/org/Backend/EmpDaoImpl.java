package org.Backend;

import java.util.List;

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
public class EmpDaoImpl implements EmpDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	@Override
	public void saveEmployee(Employee input) {
		sessionFactory.getCurrentSession().save(input);
	}
	
	
	@Transactional
	@Override
	public void updateEmployee(String oldName, String newName) {	
		
	    CriteriaBuilder cbuilder = sessionFactory.getCriteriaBuilder();
		CriteriaUpdate<Employee> update = cbuilder.createCriteriaUpdate(Employee.class);
		Root<Employee> root = update.from(Employee.class);		
	    update.set(root.get(Employee_.name), newName).where(cbuilder.equal(root.get(Employee_.name), oldName));
	    
		sessionFactory.getCurrentSession().createQuery(update).executeUpdate();
	}
	

	@Transactional
	@Override
	public List<Employee> selectEmployee(String inputName) {
		
		CriteriaBuilder cbuilder = sessionFactory.getCriteriaBuilder();
		CriteriaQuery<Employee> query = cbuilder.createQuery(Employee.class);
		Root<Employee> root = query.from(Employee.class);
		query.where( cbuilder.equal( root.get( Employee_.name ), inputName ) );
		TypedQuery<Employee> queryExecuted = sessionFactory.getCurrentSession().createQuery(query);
				
		return queryExecuted.getResultList();		
	}


	@Transactional
	@Override
	public int deleteEmployee(String inputName) {
		
		CriteriaDelete<Employee> query = sessionFactory.getCriteriaBuilder().createCriteriaDelete(Employee.class);
		query.from(Employee.class);
		sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
		
		return selectEmployee(inputName).size();
	}

}












//public <T extends PostModerate> int flagSpam(
//        EntityManager entityManager,
//        Class<T> postModerateClass) {
//         
//    CriteriaBuilder builder = entityManager
//        .getCriteriaBuilder();
//         
//    CriteriaUpdate<T> update = builder
//        .createCriteriaUpdate(postModerateClass);
// 
//    Root<T> root = update.from(postModerateClass);
// 
//    Expression<Boolean> filterPredicate = builder
//    .like(
//        builder.lower(root.get("message")),
//        "%spam%"
//    );
// 
//    if(Post.class.isAssignableFrom(postModerateClass)) {
//        filterPredicate = builder.or(
//            filterPredicate, builder
//            .like(
//                builder.lower(root.get("title")),
//                "%spam%"
//            )
//        );
//    }
// 
//    update
//    .set(root.get("status"), PostStatus.SPAM)
//    .set(root.get("updatedOn"), new Date())
//    .where(filterPredicate);
// 
//    return entityManager
//    .createQuery(update)
//    .executeUpdate();
//}











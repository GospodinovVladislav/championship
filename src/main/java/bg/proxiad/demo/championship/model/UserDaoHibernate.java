package bg.proxiad.demo.championship.model;

import java.util.Collection;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoHibernate implements UserDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveOrUpdate(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);		
	}

	@Override
	public User load(Long id) {
		return sessionFactory.getCurrentSession().load(User.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<User> listAll() {
		return sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.list();
	}

	@Override
	public void deleteUser(Long id) {
		User user = load(id);
		sessionFactory.getCurrentSession().delete(user);
		
	}

	
	
}

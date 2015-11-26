package bg.proxiad.demo.championship.model;

import java.util.Collection;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SetDaoHibernate implements SetDao{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveOrUpdate(Set set) {
		sessionFactory.getCurrentSession().saveOrUpdate(set);
	}

	@Override
	public Set load(Long id) {
		return sessionFactory.getCurrentSession().load(Set.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Set> listAll() {
		return sessionFactory.getCurrentSession()
				.createCriteria(Set.class)
				.list();
	}

	@Override
	public void deleteSet(Long id) {
		Set set = load(id);
		sessionFactory.getCurrentSession().delete(set);
	}
	
}

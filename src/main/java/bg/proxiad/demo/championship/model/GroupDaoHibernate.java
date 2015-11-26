package bg.proxiad.demo.championship.model;

import java.util.Collection;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GroupDaoHibernate implements GroupDao{

	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void saveOrUpdate(Group group) {
		sessionFactory.getCurrentSession().saveOrUpdate(group);
	}

	@Override
	public Group load(Long id) {
		return sessionFactory.getCurrentSession().load(Group.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Group> listAll() {
		return sessionFactory.getCurrentSession()
				.createCriteria(Group.class)
				.list();
	}

	@Override
	public void deleteGroup(Long id) {
		Group group = load(id);
		sessionFactory.getCurrentSession().delete(group);
	}

}

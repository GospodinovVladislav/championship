package bg.proxiad.demo.championship.model;

import java.util.Collection;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MatchDaoHibernate implements MatchDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveOrUpdate(Match match) {
		sessionFactory.getCurrentSession().saveOrUpdate(match);
	}

	@Override
	public Match load(Long id) {
		return sessionFactory.getCurrentSession().load(Match.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Match> listAll() {
		return sessionFactory.getCurrentSession()
				.createCriteria(Match.class)
				.list();
	}

	@Override
	public void deleteMatch(Long id) {
		Match match = load(id);
		sessionFactory.getCurrentSession().delete(match);
		
	}

	
	
}

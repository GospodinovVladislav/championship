package bg.proxiad.demo.championship.model;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ScoreDaoHibernate implements ScoreDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void saveOrUpdate(Score score) {
		sessionFactory.getCurrentSession().saveOrUpdate(score);
	}

	@Override
	public Score load(Long id) {
		return sessionFactory.getCurrentSession().load(Score.class, id);
	}

	@Override
	public void deleteScore(Long id) {
		Score score = load(id);
		sessionFactory.getCurrentSession().delete(score);
	}

}

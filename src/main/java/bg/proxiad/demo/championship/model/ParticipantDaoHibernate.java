package bg.proxiad.demo.championship.model;

import java.util.Collection;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ParticipantDaoHibernate implements ParticipantDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveOrUpdate(Participant participant) {
		sessionFactory.getCurrentSession().saveOrUpdate(participant);
	}

	@Override
	public Participant load(Long id) {
		return sessionFactory.getCurrentSession().load(Participant.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Participant> listAll() {
		return sessionFactory.getCurrentSession()
			.createCriteria(Participant.class)
			.list();
	}

	@Override
	public void deleteParticipant(Long id) {
		Participant participant = load(id);
		sessionFactory.getCurrentSession().delete(participant);
	}
	
	@Override
	public void updateParticipant(Participant participant){
		sessionFactory.getCurrentSession().update(participant);
	}

	
	
}

package bg.proxiad.demo.championship.model;

import java.util.Collection;

public interface ParticipantDao {

	void saveOrUpdate(Participant participant);

	Participant load(Long id);
	
	Collection<Participant> listAll();
	
	void deleteParticipant(Long id);
	
	void updateParticipant(Participant participant);

}
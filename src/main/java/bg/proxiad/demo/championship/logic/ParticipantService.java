package bg.proxiad.demo.championship.logic;

import java.util.Collection;

import bg.proxiad.demo.championship.model.Participant;

public interface ParticipantService {
	
	Participant loadParticipant(Long id);
	
	void saveOrUpdateParticipant(Participant participant);
	
	Collection<Participant> listAllParticipants();
	
	void deleteParticipant(Long id);
}

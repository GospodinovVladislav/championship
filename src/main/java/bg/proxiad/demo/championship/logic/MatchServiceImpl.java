package bg.proxiad.demo.championship.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.proxiad.demo.championship.model.Group;
import bg.proxiad.demo.championship.model.Match;
import bg.proxiad.demo.championship.model.MatchDao;
import bg.proxiad.demo.championship.model.Participant;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {

	@Autowired
	private MatchDao matchDao;

	@Override
	public Match loadMatch(Long id) {
		return matchDao.load(id);
	}

	@Override
	public void saveOrUpdateMatch(Match match) {
		matchDao.saveOrUpdate(match);
	}

	@Override
	public Collection<Match> listAllMatches() {
		return matchDao.listAll();
	}

	@Override
	public void deleteMatch(Long id) {
		matchDao.deleteMatch(id);
	}

	@Override
	public void generateMatches(Group group) {

		List<Participant> listParticipants = group.getParticipants();
		List<Match> allMatches = new ArrayList<>();

		for (int i = 0; i < group.getNumberParticipants() - 1; i++) {
			for (int j = i + 1; j < group.getNumberParticipants(); j++) {
				Match match = new Match();
				match.setHost(listParticipants.get(i));
				match.setGuest(listParticipants.get(j));
				match.setMatchGroup(group);
				matchDao.saveOrUpdate(match);
				allMatches.add(match);
			}
		}
		
		for(int i = group.getNumberParticipants() - 1; i >= 1;i--){
			for(int j = i -1; j >= 0;j--){
				Match match = new Match();
				match.setHost(listParticipants.get(i));
				match.setGuest(listParticipants.get(j));
				match.setMatchGroup(group);
				matchDao.saveOrUpdate(match);
				allMatches.add(match);
			}
		}
		
		group.setMatches(allMatches);

	}

	@Override
	public void generateQuarterFinalsMatches(Group group) {
		
		List<Participant> participants = group.getQuarterFinalsParticipants();
		List<Match> matches = new ArrayList<>();
		
		for(int i = 0; i < participants.size() / 2;i++){
			Match match = new Match();
			match.setHost(participants.get(i));
			match.setGuest(participants.get(i+2));
			match.setMatchGroup(group);
			matchDao.saveOrUpdate(match);
			matches.add(match);
		}
		
		group.setMatches(matches);
		
		
	}

	@Override
	public void generateSemiFinalsMatches(Group group) {
		
		Match match = new Match();
		match.setHost(group.getSemiFinalsParticipants().get(0));
		match.setGuest(group.getSemiFinalsParticipants().get(1));
		match.setMatchGroup(group);
		matchDao.saveOrUpdate(match);
		
		List<Match> matches = new ArrayList<>();
		matches.add(match);
		
		group.setMatches(matches);
		
	}

	@Override
	public void generateFinalsMatches(Group group) {

		Match match = new Match();
		match.setHost(group.getFinalsParticipants().get(0));
		match.setGuest(group.getFinalsParticipants().get(1));
		match.setMatchGroup(group);
		matchDao.saveOrUpdate(match);
		
		List<Match> matches = new ArrayList<>();
		matches.add(match);
		
		group.setMatches(matches);
		
	}
	

}

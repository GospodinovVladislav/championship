package bg.proxiad.demo.championship.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.proxiad.demo.championship.model.Group;
import bg.proxiad.demo.championship.model.GroupDao;
import bg.proxiad.demo.championship.model.Match;
import bg.proxiad.demo.championship.model.Participant;

@Service
@Transactional
public class GroupsServiceImpl implements GroupsService {

	@Autowired
	private GroupDao groupDao;
	
	@Override
	public Group loadGroup(Long id) {
		return groupDao.load(id);
	}

	@Override
	public void saveOrUpdateGroup(Group group) {
		groupDao.saveOrUpdate(group);
	}

	@Override
	public Collection<Group> listAllGroups() {
		return groupDao.listAll();
	}

	@Override
	public void deleteGroup(Long id) {
		groupDao.deleteGroup(id);
	}

	@Override
	public void generateQuarterFinalGroups() {

		List<Group> listGroups = (List<Group>) listAllGroups();

		int numberOfGroups = listGroups.size();

		int partPerGroup = 8 / numberOfGroups;
		int bestScorrers = 8 % numberOfGroups;

		List<Participant> participantsGroup1 = new ArrayList<>();
		List<Participant> participantsGroup2 = new ArrayList<>();

		List<Participant> allParticipants = new ArrayList<>();

		for (Group group : listGroups) {

			allParticipants.addAll(group.getParticipants());

			// Using participants per Group
			for (int i = 0; i < partPerGroup; i++) {

				List<Participant> participants = group.getParticipants();
				participants.removeAll(participantsGroup1);
				participants.removeAll(participantsGroup2);

				Collections.sort(participants, new Comparator<Participant>() {
					@Override
					public int compare(Participant p1, Participant p2) {
						return p2.getScore().getMatchesWon() - p1.getScore().getMatchesWon();
					}
				});

				// First two of the group
				Participant bestParticipant = participants.get(0);

				if (participants.size() > 1) {

					Participant secondBestParticipant = participants.get(1);

					// If they have the same MatchWon but different score swap
					if (bestParticipant.getScore().getMatchesWon() == secondBestParticipant.getScore()
							.getMatchesWon()) {
						if (secondBestParticipant.getScore().getScore() > bestParticipant.getScore().getScore()) {
							Participant swap = bestParticipant;
							bestParticipant = secondBestParticipant;
							secondBestParticipant = swap;
						}
					}
				}
				if (i % 2 == 0) {
					participantsGroup1.add(bestParticipant);
				} else {
					participantsGroup2.add(bestParticipant);
				}

			}
		}

		// Bonus Participants
		for (int i = 0; i < bestScorrers; i++) {
			allParticipants.removeAll(participantsGroup1);
			allParticipants.removeAll(participantsGroup2);

			Collections.sort(allParticipants, new Comparator<Participant>() {
				@Override
				public int compare(Participant p1, Participant p2) {
					return p2.getScore().getScore() - p1.getScore().getScore();
				}
			});

			if (i % 2 == 0) {
				participantsGroup1.add(allParticipants.get(0));
			} else {
				participantsGroup2.add(allParticipants.get(0));
			}

		}

		for (Participant p : participantsGroup1) {
			System.out.println(p.getFirstName() + " " + p.getLastName());
		}
		for (Participant p : participantsGroup2) {
			System.out.println(p.getFirstName() + " " + p.getLastName());
		}
		
		
		Group quarterFinalGroup1 = new Group();
		quarterFinalGroup1.setGroupName("Quar One");
		quarterFinalGroup1.setStage("quarter");
		for(Participant p : participantsGroup1){
			p.setQuarterParticipantGroup(quarterFinalGroup1);
		}
		quarterFinalGroup1.setQuarterFinalsParticipants(participantsGroup1);
		quarterFinalGroup1.setNumberParticipants(participantsGroup1.size());
		saveOrUpdateGroup(quarterFinalGroup1);
		
		
		Group quarterFinalGroup2 = new Group();
		quarterFinalGroup2.setGroupName("Quar Two");
		quarterFinalGroup2.setStage("quarter");
		
		for(Participant p : participantsGroup2){
			p.setQuarterParticipantGroup(quarterFinalGroup2);
		}
		
		quarterFinalGroup2.setQuarterFinalsParticipants(participantsGroup2);
		quarterFinalGroup2.setNumberParticipants(participantsGroup2.size());
		saveOrUpdateGroup(quarterFinalGroup2);

	}

	
	@Override
	public void generateSemiFinalsGroups() {
		
		List<Group> groups = (List<Group>) listAllGroups();
		List<Group> quarterFinalsGroups = new ArrayList<>();
		List<Group> semiFinalsGroups = new ArrayList<>();
		
		for(Group group : groups){
			if(group.getStage().equals("quarter")){
				quarterFinalsGroups.add(group);
			}
		}
		
		Group quarter1 = quarterFinalsGroups.get(0);
		Group quarter2 = quarterFinalsGroups.get(1);
		
		Participant quarter1Winner1 = quarter1.getMatches().get(0).getWinner();
		Participant quarter1Winner2 = quarter1.getMatches().get(1).getWinner();	
		
		Participant quarter2Winner1 = quarter2.getMatches().get(0).getWinner();
		Participant quarter2Winner2 = quarter2.getMatches().get(1).getWinner();
		
		List<Participant> semi1Participants = new ArrayList<>();
		semi1Participants.add(quarter1Winner1);
		semi1Participants.add(quarter2Winner2);
		
		List<Participant> semi2Participants = new ArrayList<>();
		semi2Participants.add(quarter2Winner1);
		semi2Participants.add(quarter1Winner2);
		
		Group semi1 = new Group();
		semi1.setGroupName("Semi One");
		semi1.setStage("semi");
		
		for(Participant p : semi1Participants){
			p.setSemiFinalsGroup(semi1);
		}
		semi1.setSemiFinalsParticipants(semi1Participants);
		semi1.setNumberParticipants(semi1Participants.size());
		saveOrUpdateGroup(semi1);
		
		
		
		Group semi2 = new Group();
		semi2.setGroupName("Semi Two");
		semi2.setStage("semi");
		
		for(Participant p : semi2Participants){
			p.setSemiFinalsGroup(semi2);
		}
		
		semi2.setSemiFinalsParticipants(semi2Participants);
		semi2.setNumberParticipants(semi2Participants.size());
		saveOrUpdateGroup(semi2);
				
				
	}

	@Override
	public void generateFinalsGroups() {
		
		List<Group> groups = (List<Group>) listAllGroups();
		List<Group> semiFinalGroups = new ArrayList<>();
		
		for(Group group : groups){
			if(group.getStage().equals("semi")){
				semiFinalGroups.add(group);
			}
		}
		
		Group firstSemi = semiFinalGroups.get(0);
		Group secondSemi = semiFinalGroups.get(1);
		
		Participant semi1Winner;
		Participant semi1Loser;
		Participant semi2Winner;
		Participant semi2Loser;
		
		Match semi1Match = firstSemi.getMatches().get(0);
		Match semi2Match = secondSemi.getMatches().get(0);
		
		
		if(semi1Match.getHost().getId() == semi1Match.getWinner().getId()){
			semi1Winner = semi1Match.getHost();
			semi1Loser = semi1Match.getGuest();
		} else {
			semi1Winner = semi1Match.getGuest();
			semi1Loser = semi1Match.getHost();
		}
		
		if(semi2Match.getHost().getId() == semi2Match.getWinner().getId()){
			semi2Winner = semi2Match.getHost();
			semi2Loser = semi2Match.getGuest();
		} else {
			semi2Winner = semi2Match.getGuest();
			semi2Loser = semi2Match.getHost();
		}
		
		
		List<Participant> thirdPlaceParticipants = new ArrayList<>();
		thirdPlaceParticipants.add(semi2Loser);
		thirdPlaceParticipants.add(semi1Loser);
		
		Group thirdPlace = new Group();
		thirdPlace.setGroupName("Third");
		thirdPlace.setStage("final");
		
		for(Participant p : thirdPlaceParticipants){
			p.setFinalsGroup(thirdPlace);
		}
		
		thirdPlace.setFinalsParticipants(thirdPlaceParticipants);
		thirdPlace.setNumberParticipants(thirdPlaceParticipants.size());
		saveOrUpdateGroup(thirdPlace);
		
		
		List<Participant> firstPlaceParticipants = new ArrayList<>();
		firstPlaceParticipants.add(semi2Winner);
		firstPlaceParticipants.add(semi1Winner);
		
		Group first = new Group();
		first.setGroupName("First");
		first.setStage("final");
		
		for(Participant p : firstPlaceParticipants){
			p.setFinalsGroup(first);
		}
		
		first.setFinalsParticipants(firstPlaceParticipants);
		first.setNumberParticipants(firstPlaceParticipants.size());
		saveOrUpdateGroup(first);
		
		
	}
}

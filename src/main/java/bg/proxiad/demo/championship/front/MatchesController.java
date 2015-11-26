package bg.proxiad.demo.championship.front;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bg.proxiad.demo.championship.logic.GroupsService;
import bg.proxiad.demo.championship.logic.MatchService;
import bg.proxiad.demo.championship.logic.ParticipantService;
import bg.proxiad.demo.championship.logic.SetService;
import bg.proxiad.demo.championship.model.Group;
import bg.proxiad.demo.championship.model.Match;
import bg.proxiad.demo.championship.model.Participant;
import bg.proxiad.demo.championship.model.Set;

@Controller
@RequestMapping("/matches")
public class MatchesController {

	@Autowired
	private GroupsService groupsService;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private ParticipantService participantsService;
	
	@Autowired
	private SetService setService;

	@RequestMapping(method = RequestMethod.GET)
	public String listMatches(ModelMap model) {

		List<Group> listGroup = (List<Group>) groupsService.listAllGroups();

		List<String> groupNames = new ArrayList<String>();

		for (Group group : listGroup) {
			groupNames.add(group.getGroupName());
		}
		
//		Match match = new Match();
//		Set set1 = new Set();
//		Set set2 = new Set();
//		
//		setService.saveOrUpdateSet(set1);
//		setService.saveOrUpdateSet(set2);
//		
//		set1.setMatch(match);
//		set2.setMatch(match);
//		
//		
//		List<Set> listSets = new ArrayList<>();
//		listSets.add(set1);
//		listSets.add(set2);
//		
//		
//		matchService.saveOrUpdateMatch(match);

		model.addAttribute("groupNames", groupNames);
		return "list-matches";
	}

	
	@RequestMapping("{matchID}/editMatchScore")
	public String editMatchScore(@PathVariable("matchID") Long matchID,ModelMap model){
		
		Match match = matchService.loadMatch(matchID);
		
		Double num= 100d;
		System.out.println(String.valueOf(num));
		
		model.addAttribute("match",match);
		return "editMatchScore";
	}
	
	@RequestMapping("{matchID}/editMatchScore/{setOneHost}/{setOneGuest}/{setTwoHost}/{setTwoGuest}/{setThreeHost}/{setThreeGuest}")
	public void editMatchScoreParams(@PathVariable("matchID") Long matchID,
			@PathVariable("setOneHost") short setOneHost, @PathVariable("setOneGuest") short setOneGuest,
			@PathVariable("setTwoHost") short setTwoHost, @PathVariable("setTwoGuest") short setTwoGuest,
			@PathVariable("setThreeHost") short setThreeHost, @PathVariable("setThreeGuest") short setThreeGuest,
				ModelMap model){
		
		
		
		Match match = matchService.loadMatch(matchID);
		Participant host = match.getHost();
		Participant guest = match.getGuest();
		List<Set> sets = new ArrayList<>();
		
		Group group = match.getMatchGroup();
		
		//Winner
		int hostSets = 0;
		int guestSets = 0;
		
		
		if(setOneHost > setOneGuest){
			hostSets++;
		} else {
			guestSets++;
		}
		
		if(setTwoHost > setTwoGuest){
			hostSets++;
		} else {
			guestSets++;
		}
		
		if(hostSets == guestSets){
			if(setThreeHost > setThreeGuest){
				hostSets++;
			} else {
				guestSets++;
			}
		}
		
		if(hostSets > guestSets){
			match.setWinner(host);
			
			
			if(group.getStage().equals("group")){
			
			
			host.getScore().setMatchesWon(host.getScore().getMatchesWon() + 1);
			guest.getScore().setMatchesLost(guest.getScore().getMatchesLost() + 1);
			
			if(hostSets - guestSets == 2){
				host.getScore().setScore(host.getScore().getScore() + 3);
				guest.getScore().setScore(guest.getScore().getScore() + 0);
			} else {
				host.getScore().setScore(host.getScore().getScore() + 2);
				guest.getScore().setScore(guest.getScore().getScore() + 1);
			}
			
			}
			
		} else {
			match.setWinner(guest);
			
			if(group.getStage().equals("group")){
			
			guest.getScore().setMatchesWon(guest.getScore().getMatchesWon() + 1);
			host.getScore().setMatchesLost(host.getScore().getMatchesLost() + 1);
			
			if(guestSets - hostSets == 2){
				guest.getScore().setScore(guest.getScore().getScore() + 3);
				host.getScore().setScore(host.getScore().getScore() + 0);
			} else {
				guest.getScore().setScore(guest.getScore().getScore() + 2);
				host.getScore().setScore(host.getScore().getScore() + 1);
			}
			
			}
			
		}
		
		
		
		//Manage Sets
		Set set1 = new Set();
		set1.setHostPoints(setOneHost);
		set1.setGuestPoints(setOneGuest);
		set1.setMatch(match);
		
		setService.saveOrUpdateSet(set1);
		sets.add(set1);
		
		Set set2 = new Set();
		set2.setHostPoints(setTwoHost);
		set2.setGuestPoints(setTwoGuest);
		set2.setMatch(match);
		
		setService.saveOrUpdateSet(set2);
		sets.add(set2);
		
		if(setThreeHost != 0  && setThreeGuest != 0){
			Set set3 = new Set();
			set3.setHostPoints(setThreeHost);
			set3.setGuestPoints(setThreeGuest);
			set3.setMatch(match);
			
			setService.saveOrUpdateSet(set3);
			sets.add(set3);
		}
		
		match.setSets(sets);
		
		matchService.saveOrUpdateMatch(match);
		
		//Update Participants Score
		
		if(group.getStage().equals("group")){
		
		host.getScore().setPointsMade(host.getScore().getPointsMade() + setOneHost + setTwoHost + setThreeHost); 
		guest.getScore().setPointsMade(guest.getScore().getPointsMade() + setOneGuest + setTwoGuest + setThreeGuest); 
		
		host.getScore().setPointsTaken(host.getScore().getPointsTaken() + setOneGuest + setTwoGuest + setThreeGuest);
		guest.getScore().setPointsTaken(guest.getScore().getPointsTaken() + setOneHost + setTwoHost + setThreeHost);
		
		participantsService.saveOrUpdateParticipant(host);
		participantsService.saveOrUpdateParticipant(guest);
		//Participants Score
		
		}
		
		
	}
	
	@RequestMapping(value = "/showGroup/{groupName}", method = RequestMethod.GET)
	public String showMatches(@PathVariable("groupName") String groupName, ModelMap model) {
		
		List<Group> listGroup = (List<Group>) groupsService.listAllGroups();
		Group selectedGroup = null;
		
		for (Group group : listGroup){
			if(group.getGroupName().equals(groupName)){
				selectedGroup = group;
				break;
			}
		}
		
		//matchService.generateMatches(selectedGroup);
		List<Match> listMatches = selectedGroup.getMatches();
		
		model.addAttribute("group",selectedGroup);
		model.addAttribute("listMatches", listMatches);
		return "groupMatches";
	}

}

package bg.proxiad.demo.championship.front;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bg.proxiad.demo.championship.logic.GroupsService;
import bg.proxiad.demo.championship.logic.MatchService;
import bg.proxiad.demo.championship.logic.ParticipantService;
import bg.proxiad.demo.championship.logic.ScoreService;
import bg.proxiad.demo.championship.logic.SetService;
import bg.proxiad.demo.championship.model.Group;
import bg.proxiad.demo.championship.model.Match;
import bg.proxiad.demo.championship.model.Participant;
import bg.proxiad.demo.championship.model.Score;
import bg.proxiad.demo.championship.model.Set;

@Controller
@RequestMapping("/groups")
public class GroupController {
	
	@Autowired
	private GroupsService groupsService;
	
	@Autowired
	private SetService setService;
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private ParticipantService participantService;
	
	@Autowired
	private MatchService matchService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String listGroups(ModelMap model) {
		
		
//		Participant part = new Participant();
//		
//		part.setFirstName("asas");
//		
//		participantService.saveOrUpdateParticipant(part);
//		
//		List<Participant> list = new ArrayList();
//		list.add(part);
//		
//		
//		Match match1 = new Match();
//		Match match2 = new Match();
//		
//		matchService.saveOrUpdateMatch(match1);
//		matchService.saveOrUpdateMatch(match2);
//		
//		List<Match> listMatches = new ArrayList<>();
//		listMatches.add(match1);
//		listMatches.add(match2);
//		
//		
//		Group group = new Group();
//		group.setMatches(listMatches);
//		group.setGroupName("Group B");
//		group.setParticipants(list);
//		
//		groupsService.saveOrUpdateGroup(group);
//		
		
		model.addAttribute("groups", groupsService.listAllGroups());
		return "list-groups";
	}
	
	@RequestMapping("/{groupID}/edit")
	public String editGroup(ModelMap model,@PathVariable("groupID") Long groupID){
		
		Group group = groupsService.loadGroup(groupID);
		
		List<Participant> freeParticipants = getFreeParticipants();
		
		model.addAttribute("freeParticipants",freeParticipants);
		
		
		for(Participant p : group.getParticipants()){
			System.out.println(p.getFirstName());
		}
		
		model.addAttribute("group", group);
		return "editGroup";
	}
	
	
	@RequestMapping("/{groupID}/delete")
	public String deleteGroup(@PathVariable("groupID") Long groupID){
		Group group = groupsService.loadGroup(groupID);
		
		List<Participant> participants = group.getParticipants();
		List<Match> listMatches = group.getMatches();
		
		for(Match match : listMatches){
			
			List<Set> sets = match.getSets();
			match.setSets(null);
			
			if(sets != null){
				for(Set set:sets){
					set.setMatch(null);
					setService.deleteSet(set.getId());
				}
			}
			
			matchService.deleteMatch(match.getId());
		}
		
		for(Participant p : participants){
			p.setisInGroup(false);
			p.setParticipantGroup(null);
			p.getScore().setMatchesLost(0);
			p.getScore().setMatchesWon(0);
			p.getScore().setPointsMade(0);
			p.getScore().setPointsTaken(0);
			p.getScore().setScore(0);
			participantService.saveOrUpdateParticipant(p);
		}
		
		groupsService.deleteGroup(groupID);
		return "redirect:/app/groups";
	}
	
	@RequestMapping(value = "/createGroup",method = RequestMethod.POST)
	public String createGroup(ModelMap model,
							@RequestParam("group_name") String groupName,
							@RequestParam("number_players") String numberPlayers){
		
		
		int numbers = switchNumberPlayer(numberPlayers);
		
		List<Participant> freeParticipants = getFreeParticipants();
		
		Group group = new Group();
		group.setGroupName(groupName);
		group.setNumberParticipants(numbers);
		
		groupsService.saveOrUpdateGroup(group);
		
		model.addAttribute("groupName", groupName);
		model.addAttribute("nubmerOfPersons", numbers);
		model.addAttribute("freeParticipants", freeParticipants);
		return "createGroup";
	}
	
	@RequestMapping("/editGroupParticipants/{groupID}")
	public String editGroupParticipants(@PathVariable("groupID") long id,ModelMap model){
		
		
		Group group = groupsService.loadGroup(id);
		
		int currentParticipantsNumber = group.getParticipants().size();
				
		List<Participant> freeParticipants = getFreeParticipants();
		
		model.addAttribute("groupName", group.getGroupName());
		model.addAttribute("nubmerOfPersons", group.getNumberParticipants() - currentParticipantsNumber);
		model.addAttribute("freeParticipants", freeParticipants);
		return "createGroup";
	}
	
	private List<Participant> getFreeParticipants(){
		
		List<Participant> list = (List<Participant>) participantService.listAllParticipants();
		List<Participant> freeParticipants = new ArrayList<Participant>();
		
		
		for(Participant p : list){
			System.out.println(p.getFirstName());
			System.out.println(p.getisInGroup());
			if(!p.getisInGroup()){
				freeParticipants.add(p);
			}
		}
		
		return freeParticipants;
		
	}
	
	@RequestMapping(value = "/{groupName}/addParticipants/{partID}",method = RequestMethod.GET)
	public void addParticipants(@PathVariable("partID") Long partID,
									@PathVariable("groupName") String groupName){
		
		List<Group> listGroups = (List<Group>) groupsService.listAllGroups();
		Group selectedGroup = null;
		Participant selectedParticipant = participantService.loadParticipant(partID);
		
		for(Group group : listGroups){
			if(group.getGroupName().equals(groupName)){
				selectedGroup = group;
			}
		}
		
		selectedGroup.setStage("group");
		selectedParticipant.setParticipantGroup(selectedGroup);
		selectedParticipant.setisInGroup(true);
		
		participantService.saveOrUpdateParticipant(selectedParticipant);
		
	}
	
	@RequestMapping("/{groupName}/checkGroupName")
	@ResponseBody
	public String checkGroupName(@PathVariable("groupName") String groupName) {
		System.out.println("exist " + groupName);

		List<Group> listGroups = (List<Group>) groupsService.listAllGroups();

		for (Group u : listGroups) {
			if (u.getGroupName().equals(groupName)) {
				return "Group with that name already exist";
			}
		}
		return "";
	}
	
	@RequestMapping(value = "/removeParticipants/{id}",method = RequestMethod.GET)
	public void removeParticipants(@PathVariable("id") Long id){
		
		Participant selectedParticipant = participantService.loadParticipant(id);
		
		System.out.println("REMOVE");
		System.out.println(id);
		
		
		selectedParticipant.setisInGroup(false);
		selectedParticipant.setParticipantGroup(null);
		
		participantService.saveOrUpdateParticipant(selectedParticipant);
		
	}
	
	
	
	private int switchNumberPlayer(String numberPlayers){
		switch (numberPlayers) {
		case "one":
			return 1;
		case "two":
			return 2;
		case "three":
			return 3;
		case "four":
			return 4;
		default:
			return 5;

		}
	}
	

}

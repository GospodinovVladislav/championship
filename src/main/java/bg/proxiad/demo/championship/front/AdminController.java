package bg.proxiad.demo.championship.front;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bg.proxiad.demo.championship.logic.GroupsService;
import bg.proxiad.demo.championship.logic.MatchService;
import bg.proxiad.demo.championship.logic.ParticipantService;
import bg.proxiad.demo.championship.model.Group;

@Controller
@RequestMapping("/admin")
public class AdminController {

	
	@Autowired
	private GroupsService groupsService;
	
	@Autowired
	private MatchService matchService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String adminPanel(){
		return "adminPanel";
	}
	
	
	@RequestMapping(value = "/generateGroupMatches",method = RequestMethod.GET)
	public String generateGroupMatches(){
		
		List<Group> listGroups = (List<Group>) groupsService.listAllGroups();
		
		for(Group group:listGroups){
			matchService.generateMatches(group);
		}
		return "adminPanel";
	}
	
	@RequestMapping(value = "/generateQuarterFinals",method = RequestMethod.GET)
	public String generateQuarterFinals(){
		
		groupsService.generateQuarterFinalGroups();
		
		return "adminPanel";
	}
	
	
	@RequestMapping(value = "/generateQuarterFinalsMatches",method = RequestMethod.GET)
	public String generateQuarterFinalsMatches(){
		
		List<Group> groups = (List<Group>) groupsService.listAllGroups();
		
		for(Group group : groups){
			if(group.getStage().equals("quarter")){
				matchService.generateQuarterFinalsMatches(group);
			}
		}
		
		return "adminPanel";
	}
	
	@RequestMapping(value = "/generateSemiFinals",method = RequestMethod.GET)
	public String generateSemiFinals(){
		
		groupsService.generateSemiFinalsGroups();
		
		return "adminPanel";
	}
	
	@RequestMapping(value = "/generateFinals",method = RequestMethod.GET)
	public String generateFinals(){
		
		groupsService.generateFinalsGroups();
		
		return "adminPanel";
	}
	
	@RequestMapping(value = "/generateFinalsMatches",method = RequestMethod.GET)
	public String generateFinalsMatches(){
		
		List<Group> groups = (List<Group>) groupsService.listAllGroups();
		
		for(Group group : groups){
			if(group.getStage().equals("final")){
				matchService.generateFinalsMatches(group);
			}
		}
		
		return "adminPanel";
	}
	
	@RequestMapping(value = "/generateSemiFinalsMatches",method = RequestMethod.GET)
	public String generateSemiFinalsMatches(){
		
		List<Group> groups = (List<Group>) groupsService.listAllGroups();
		
		for(Group group : groups){
			if(group.getStage().equals("semi")){
				matchService.generateSemiFinalsMatches(group);
			}
		}
		
		return "adminPanel";
	}
	
}

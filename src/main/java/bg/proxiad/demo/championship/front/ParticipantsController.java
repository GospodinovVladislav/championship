package bg.proxiad.demo.championship.front;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bg.proxiad.demo.championship.logic.GroupsService;
import bg.proxiad.demo.championship.logic.ParticipantService;
import bg.proxiad.demo.championship.logic.ScoreService;
import bg.proxiad.demo.championship.logic.UserService;
import bg.proxiad.demo.championship.model.Group;
import bg.proxiad.demo.championship.model.Participant;
import bg.proxiad.demo.championship.model.PictureOperations;
import bg.proxiad.demo.championship.model.Score;
import bg.proxiad.demo.championship.model.User;

@Controller
@RequestMapping("/participants")
public class ParticipantsController {

	@Autowired
	private ParticipantService participantService;
	
	@Autowired
	private UserService usersService;
	
	@Autowired
	private GroupsService groupsService;
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	ServletContext context;

	@RequestMapping(method = RequestMethod.GET)
	public String listParticipants(ModelMap model) {
		
		
//		Score score = new Score();
//		
//		Group group = new Group();
//		group.setGroupName("Gang of Four New");
//		
//		
//		Participant participant = new Participant();
//		Participant participant1 = new Participant();
//		Participant participant2 = new Participant();
//		Participant participant3 = new Participant();
//		
//		participant.setFirstName("First");
//		participant.setScore(score);
//		
//		scoreService.saveOrUpdateScore(score);
//		
//		participant1.setFirstName("Second");
//		participant2.setFirstName("Third");
//		participant3.setFirstName("Forth");
//		
//		
//		groupsService.saveOrUpdateGroup(group);
//		participantService.saveOrUpdateParticipant(participant);
//		participantService.saveOrUpdateParticipant(participant1);
//		participantService.saveOrUpdateParticipant(participant2);
//		participantService.saveOrUpdateParticipant(participant3);
		
		
		model.addAttribute("participants", participantService.listAllParticipants());
		return "list-participants";
	}
	
	
	@RequestMapping("/{partID}/delete")
	public String deleteParticipant(@PathVariable("partID") Long partID){
		
		Participant p = participantService.loadParticipant(partID);
		
		if(p.getScore() != null){
			Score score = p.getScore();
			score.setParticipant(null);
			scoreService.deleteScore(score.getId());
			p.setScore(null);
		}
		
		List<User> listUsers = (List<User>) usersService.listAllUsers();
		boolean isUser = false;
		
		for(User u : listUsers){
			if(p.getEmail().equals(u.getEmail())){
				isUser = true;
				break;
			}
		}
		
		
		if(!isUser){
			PictureOperations.deletePicture(p.getPhotoFileName(), context);
		}
		
		
		participantService.deleteParticipant(partID);
		return "redirect:/app/participants";
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public String addParticipant(@RequestParam("first_name") String firstName,
								@RequestParam("last_name") String lastName,
								@RequestParam("e-mail") String mail){
		
		Participant participant = new Participant();
		
		participant.setFirstName(firstName);
		participant.setLastName(lastName);
		participant.setEmail(mail);
		participantService.saveOrUpdateParticipant(participant);
		return "redirect:/app/participants";
	}
	
	@RequestMapping("/edit")
	public String editParticipant(@RequestParam("first_name") String firstName,
								@RequestParam("last_name") String lastName,
								@RequestParam("e-mail") String mail,
								@RequestParam("id") long id){
		
		Participant participant = participantService.loadParticipant(id);
		participant.setFirstName(firstName);
		participant.setLastName(lastName);
		participant.setEmail(mail);
		participantService.saveOrUpdateParticipant(participant);
		
		return "redirect:/app/participants";
	}
	
	@RequestMapping("/addFromUsers/{userID}")
	public String addFromUsers(@PathVariable("userID") long userID){
		
		List<Participant> participants = (List<Participant>) participantService.listAllParticipants();
		User user = usersService.loadUser(userID);
		
		
		for (Participant p : participants){
			System.out.println(p.getEmail());
			System.out.println(user.getEmail());
			if(p.getEmail().equals(user.getEmail())){
				return "alert";
			}
		}
		Score score = new Score();
		
		Participant participant = new Participant();
		participant.setFirstName(user.getFirstName());
		participant.setLastName(user.getLastName());
		participant.setEmail(user.getEmail());
		participant.setPhotoFileName(user.getPhotoFileName());
		
		scoreService.saveOrUpdateScore(score);
		
		participant.setScore(score);
		score.setParticipant(participant);

		participantService.saveOrUpdateParticipant(participant);
		
		return "redirect:/app/users";
		
	}
	
	

}

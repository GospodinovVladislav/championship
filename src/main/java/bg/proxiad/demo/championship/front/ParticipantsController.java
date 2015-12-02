package bg.proxiad.demo.championship.front;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	
	@RequestMapping(value="/add",method = RequestMethod.GET)
	public String addParticipantView(ModelMap model){
		
		Participant participant = new Participant();
		
		model.addAttribute("participant",participant);
		return "addParticipant";
	}
	
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public String addParticipant(@RequestParam(value = "picture", required = false) MultipartFile image,
								@Valid @ModelAttribute("participant") Participant participant,
								BindingResult result){
		
		List<Participant> participants = (List<Participant>) participantService.listAllParticipants();
		
		for(Participant p:participants){
			if(p.getEmail().equals(participant.getEmail())){
				result.addError(new FieldError("participant", "email", "Email already exist"));
			}
		}
		
		
		if(result.hasErrors()){
			return "addParticipant";
		}
		
		
		String folderPath = context.getRealPath("/") + "\\images\\";
		String imageName;

		if (image.getOriginalFilename().equals("")) {
			imageName = "default.jpeg";
		} else {
			int dot = image.getOriginalFilename().indexOf(".");
			String ext = image.getOriginalFilename().substring(dot, image.getOriginalFilename().length());
			imageName = participant.getFirstName() + participant.getLastName() + ext;
		}

		PictureOperations.savePicture(folderPath, imageName, image);
		participant.setPhotoFileName(imageName);
		
		
		Score score = new Score();
		scoreService.saveOrUpdateScore(score);
		score.setParticipant(participant);
		
		participantService.saveOrUpdateParticipant(participant);
		return "redirect:/app/participants";
	}
	
	
	@RequestMapping(value = "{participantID}/edit",method = RequestMethod.GET)
	public String editParticipantView(@PathVariable("participantID") long  participantID, ModelMap model){
		Participant participant = participantService.loadParticipant(participantID);
		model.addAttribute("participant",participant);
		return "editParticipant";
	}
	
	
	
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	public String editParticipant(@RequestParam(value = "picture", required = false) MultipartFile image,
								@Valid @ModelAttribute("participant") Participant participant,
								BindingResult result){
		
		
		Participant oldParticipant = participantService.loadParticipant(participant.getId());
		
		List<Participant> participants = (List<Participant>) participantService.listAllParticipants();
		
		for(Participant p:participants){
			if(p.getEmail().equals(participant.getEmail()) && !p.getEmail().equals(oldParticipant.getEmail())){
				result.addError(new FieldError("participant", "email", "Email already exist"));
			}
		}
		
		
		if(result.hasErrors()){
			return "editParticipant";
		}
		
		
		String imageName = participant.getPhotoFileName();
		if (!image.getOriginalFilename().equals("")) {

			System.out.println("In edit pic");
			
			String folderPath = context.getRealPath("/") + "\\images\\";
			
			System.out.println(folderPath);

			if (image.getOriginalFilename().equals("")) {
				imageName = "default.jpeg";
			} else {
				int dot = image.getOriginalFilename().indexOf(".");
				String ext = image.getOriginalFilename().substring(dot, image.getOriginalFilename().length());
				imageName = participant.getFirstName() + participant.getLastName() + ext;
			
			}

			PictureOperations.deletePicture(participant.getPhotoFileName(), context);
			PictureOperations.savePicture(folderPath, imageName, image);

			participant.setPhotoFileName(imageName);
		}
		System.out.println(participant.getScore());
		
		
		
		oldParticipant.setFirstName(participant.getFirstName());
		oldParticipant.setLastName(participant.getLastName());
		oldParticipant.setEmail(participant.getEmail());
		oldParticipant.setPhotoFileName(participant.getPhotoFileName());
		
		participantService.saveOrUpdateParticipant(oldParticipant);
		
		
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

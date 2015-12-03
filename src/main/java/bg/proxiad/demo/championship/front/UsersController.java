package bg.proxiad.demo.championship.front;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import bg.proxiad.demo.championship.logic.ParticipantService;
import bg.proxiad.demo.championship.logic.UserService;
import bg.proxiad.demo.championship.model.Participant;
import bg.proxiad.demo.championship.model.PictureOperations;
import bg.proxiad.demo.championship.model.User;

@Controller
@RequestMapping("/users")
public class UsersController {

	
	private UserService userService;
	
	@Autowired
	public UsersController(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	private ParticipantService participantService;

	@Autowired
	ServletContext context;

	@RequestMapping(method = RequestMethod.GET)
	public String listUsers(ModelMap model) {

		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();

//		 User user = new User();
//		
//		 user.setFirstName("Gosho");
//		 user.setLastName("Petrov");
//		 user.setEmail("mail@mails.bg");
//		 user.setPassword("1234");
//		
//		 userService.saveOrUpdateUser(user);

		model.addAttribute("users", userService.listAllUsers());
		return "list-users";
	}

	@RequestMapping("/{userID}/delete")
	public String deleteUser(@PathVariable("userID") Long userID) {

		User user = userService.loadUser(userID);
		
		boolean isParticipant = false;
		List<Participant> listParticipants = (List<Participant>) participantService.listAllParticipants();
		
		for(Participant p: listParticipants){
			if(user.getEmail().equals(p.getEmail())){
				isParticipant = true;
				break;
			}
		}
	
		if(!isParticipant){
			PictureOperations.deletePicture(user.getPhotoFileName(), context);
		}

		userService.deleteUser(userID);
		return "redirect:/app/users";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addUser(@RequestParam("first_name") String firstName, @RequestParam("last_name") String lastName,
			@RequestParam("email") String mail, @RequestParam("password") String password,
			@RequestParam(value = "picture", required = false) MultipartFile image) {

		String folderPath = context.getRealPath("/") + "\\images\\";
		String imageName;

		if (image.getOriginalFilename().equals("")) {
			imageName = "default.jpeg";
		} else {
			int dot = image.getOriginalFilename().indexOf(".");
			String ext = image.getOriginalFilename().substring(dot, image.getOriginalFilename().length());
			imageName = firstName + lastName + ext;
		}

		PictureOperations.savePicture(folderPath, imageName, image);

		User user = new User();

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(mail);
		user.setPhotoFileName(imageName);
		user.setPassword(password);

		userService.saveOrUpdateUser(user);

		UsernamePasswordToken token = new UsernamePasswordToken(mail, password);
		org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
		currentUser.login(token);

		return "redirect:/app/users";
	}

	@RequestMapping("/{userID}/openForEdit")
	public String openForEdit(@PathVariable("userID") long userID, ModelMap model) {

		User user = userService.loadUser(userID);

		model.addAttribute("email", user.getEmail());
		model.addAttribute("first_name", user.getFirstName());
		model.addAttribute("last_name", user.getLastName());
		model.addAttribute("picture", user.getPhotoFileName());
		model.addAttribute("id", userID);
		model.addAttribute("isAdmin", user.getIsAdmin());
		return "editUser";
	}

	@RequestMapping("/edit")
	public String editUser(@RequestParam("first_name") String firstName, @RequestParam("last_name") String lastName,
			@RequestParam("id") long id, @RequestParam("e-mail") String mail,
			@RequestParam(value = "picture", required = false) MultipartFile image,
			@RequestParam(value = "checkboxes", required = false) String userIds) {
		User user = userService.loadUser(id);
		
		String imageName = user.getPhotoFileName();

		if (!image.getOriginalFilename().equals("")) {

			System.out.println("In edit pic");
			
			String folderPath = context.getRealPath("/") + "\\images\\";
			
			System.out.println(folderPath);

			if (image.getOriginalFilename().equals("")) {
				imageName = "default.jpeg";
			} else {
				int dot = image.getOriginalFilename().indexOf(".");
				String ext = image.getOriginalFilename().substring(dot, image.getOriginalFilename().length());
				imageName = firstName + lastName + ext;
			
			}

			PictureOperations.deletePicture(user.getPhotoFileName(), context);
			PictureOperations.savePicture(folderPath, imageName, image);

		}

		if (userIds != null) {
			if (userIds.equals("Admin")) {
				user.setAdmin(true);
			}
		} else {
			user.setAdmin(false);
		}

		user.setPhotoFileName(imageName);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(mail);
		userService.saveOrUpdateUser(user);

		editParticipant(user);
		
		return "redirect:/app/users";
	}
	
	
	private void editParticipant(User user){
		Participant participant = null;
		
		List<Participant> listParticipants = (List<Participant>) participantService.listAllParticipants();
		
		for(Participant p : listParticipants){
			if(p.getEmail().equals(user.getEmail())){
				participant = participantService.loadParticipant(p.getId());
			}
		}
		
		if(participant != null){
			participant.setFirstName(user.getFirstName());
			participant.setLastName(user.getLastName());
			participant.setPhotoFileName(user.getPhotoFileName());
		}
	}

}

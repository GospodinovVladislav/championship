package bg.proxiad.demo.championship.front;

import java.util.List;

import javax.security.auth.Subject;
import javax.servlet.ServletContext;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import bg.proxiad.demo.championship.logic.UserService;
import bg.proxiad.demo.championship.model.PictureOperations;
import bg.proxiad.demo.championship.model.User;

@Controller
public class LoginController {

	@Autowired
	ServletContext context;

	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/logout")
	public String logout() {

		SecurityUtils.getSubject().logout();

		return "home";
	}

	@RequestMapping("/noAccess")
	public String noAccess() {
		return "noAccess";
	}

	@RequestMapping(value = "/editMyAccount", method = RequestMethod.POST)
	public String editMyAccount(@RequestParam("first_name") String firstName,
			@RequestParam("last_name") String lastName, @RequestParam("id") long id,
			@RequestParam("e-mail") String mail, @RequestParam(value = "picture", required = false) MultipartFile image,
			@RequestParam(value = "new_pass", required = false) String new_pass) {

		User user = userService.loadUser(id);
		String imageName = user.getPhotoFileName();
		
		if (!image.getOriginalFilename().equals("")) {
			
			String folderPath = context.getRealPath("/") + "\\images\\";

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

		user.setPhotoFileName(imageName);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(mail);
		
		if (!new_pass.equals("")) {
			user.setPassword(new_pass);
		}
		userService.saveOrUpdateUser(user);

		return "home";
	}

	@RequestMapping(value = "/editMyAccount", method = RequestMethod.GET)
	public String editAccount(ModelMap model) {

		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		User currentUser = (User) subject.getPrincipal();

		model.addAttribute("email", currentUser.getEmail());
		model.addAttribute("first_name", currentUser.getFirstName());
		model.addAttribute("last_name", currentUser.getLastName());
		model.addAttribute("picture", currentUser.getPhotoFileName());
		model.addAttribute("id", currentUser.getId());
		model.addAttribute("isAdmin", currentUser.getIsAdmin());
		return "editMyAccount";
	}

	@RequestMapping("/checkMail/{mail:.+}")
	@ResponseBody
	public String checkMail(@PathVariable("mail") String mail) {
		System.out.println("exist " + mail);

		List<User> listUsers = (List<User>) userService.listAllUsers();

		for (User u : listUsers) {
			if (u.getEmail().equals(mail)) {
				return "Mail already exist";
			}
		}
		return "";
	}

	@RequestMapping("/checkPass/{old_pass}")
	@ResponseBody
	public String checkPassword(@PathVariable("old_pass") String old_pass) {
		System.out.println("exist " + old_pass);

		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();

		if (user.getPassword().equals(old_pass)) {
			return "";
		} else {
			return "Password do not match";
		}

	}
}

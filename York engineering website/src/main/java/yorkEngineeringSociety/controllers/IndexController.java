package yorkEngineeringSociety.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yorkEngineeringSociety.models.User;
import yorkEngineeringSociety.services.UserService;

@Controller
@SessionAttributes("user")
public class IndexController {
	@Autowired
	private UserService userService;
	
	@ModelAttribute("user")
	public User guestUser() {
		User user = new User();
		user.setUsername("guest");
		return user;
	}

	@GetMapping({"/"})
	public String home(Model model) {

		return "index";
	}

	@PostMapping({"/login"})
	public String login(@RequestParam(required = true) String email, @RequestParam(required = true) String password,
			Model model, RedirectAttributes ra) {
		User user = this.userService.userLogin(email, password);
		if (user == null) {
			model.addAttribute("login", "failure");
		} else {
			model.addAttribute("user", user);
			model.addAttribute("login", "success");
			model.addAttribute("username", user.getUsername());
		}

		return "redirect:/";
	}

	@GetMapping({"/signup"})
	public String createAccountPage() {
		return "createAccount";
	}
	
	@RequestMapping({"/logout"})
	public String logout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "redirect:/";
	}

	@PostMapping({"/signup"})
	public String createAccountSubmit(@RequestParam(required = true) String username,
									@RequestParam(required = true) String password,
									@RequestParam(required = true) String email,
									@RequestParam(required = true) String firstname,
									@RequestParam(required = true) String lastname,
									@RequestParam(required = true) boolean isAdmin, 
									Model model) {
		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(password);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setAdmin(isAdmin);
		this.userService.saveUser(user);
		model.addAttribute("login", "successful account creation");
		return "redirect:/";
	}
	
	@GetMapping({"/createEvent"})
	public String eventCreate(Model model) {
		return "createEvent";
	}
	
	@PostMapping({"/createEvent"})
	public String eventSave(Model model, @RequestParam String edit) {
		model.addAttribute("newpage", edit);
		return "blankpage";
	}
}
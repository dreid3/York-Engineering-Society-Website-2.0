package yorkEngineeringSociety.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yorkEngineeringSociety.models.User;
import yorkEngineeringSociety.services.UserService;

@Controller
@SessionAttributes({"username"})
public class IndexController {
	@Autowired
	private UserService userService;

	@GetMapping({"/"})
	public String home(Model model) {
		if (!model.containsAttribute("username")) {
			model.addAttribute("username", "guest");
		}

		return "index";
	}

	@GetMapping({"/login"})
	public String login(@RequestParam(required = true) String email, @RequestParam(required = true) String password,
			Model model, RedirectAttributes ra) {
		User user = this.userService.userLogin(email, password);
		if (user == null) {
			model.addAttribute("login", "failure");
		} else {
			model.addAttribute("login", "success");
			model.addAttribute("username", user.getUsername());
		}

		return "redirect:/";
	}

	@GetMapping({"/signup"})
	public String createAccountPage() {
		return "createAccount";
	}

	@PostMapping({"/signup"})
	public String createAccountSubmit(@RequestParam(required = true) String username,
			@RequestParam(required = true) String password, @RequestParam(required = true) String email, Model model) {
		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(password);
		this.userService.saveUser(user);
		model.addAttribute("login", "successful account creation");
		return "index";
	}
}
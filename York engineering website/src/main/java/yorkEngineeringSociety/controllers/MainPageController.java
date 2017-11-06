package yorkEngineeringSociety.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import yorkEngineeringSociety.repos.EventRepository;

@Controller
public class MainPageController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private EventRepository eventRepository;
	
	@ModelAttribute("df")
	public DateFormat dateFormat() {
		DateFormat df = new SimpleDateFormat("MM/d/yy h:mm a");
		return df;
	}
	
	@ModelAttribute("user")
	public User guestUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      String name = auth.getName();
	      if (name != null) {
	    	  User user = userService.findUserByEmail(name);
	    	  if (user != null)
	    	  {
	    		  return user;
	    	  }
	      }
		User user = new User();
		user.setAdmin(false);
		user.setFirstname("guest");
		return user;
	}

	@GetMapping({"/"})
	public String home(Model model) {
		model.addAttribute("events", eventRepository.findAll());
		return "index";
	}
	
	@GetMapping({"/login"})
	public String login(Model model, @RequestParam(defaultValue="false") String error) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/";
		}
		model.addAttribute("error", error);
		return "loginPage";
	}

	@GetMapping({"/signup"})
	public String createAccountPage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/";
		}
		return "createAccount";
	}
	
	@RequestMapping({"/logout"})
	public String logout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "redirect:/";
	}
	
	//might move this to another page because
	// we need to add a time stamp to this that will be very important 
	@PostMapping({"/signup"})
	public String createAccountSubmit(@RequestParam(required = true) String password,
									@RequestParam(required = true) String email,
									@RequestParam(required = true) String firstname,
									@RequestParam(required = true) String lastname,
									@RequestParam(required = true, defaultValue = "false") boolean isAdmin, 
									Model model) {
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setAdmin(isAdmin);
		this.userService.saveUser(user);
		model.addAttribute("login", "Successful account creation! You may now login to your account!");
		return "index";
		
	}
	
	@GetMapping({"calendar"})
	public String calendar() {

		return "calendar";
	}
	
	@GetMapping({"contact"})
	public String contact() {

		return "contact";
	}


}
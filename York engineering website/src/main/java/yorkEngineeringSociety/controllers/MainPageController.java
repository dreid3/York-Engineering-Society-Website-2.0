package yorkEngineeringSociety.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import yorkEngineeringSociety.config.WebSecurityConfig;
import yorkEngineeringSociety.models.Event;
import yorkEngineeringSociety.models.User;
import yorkEngineeringSociety.services.UserService;
import yorkEngineeringSociety.repos.EventRepository;
import yorkEngineeringSociety.repos.UserRepository;

@Controller
public class MainPageController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private WebSecurityConfig webSecurityConfig;
	
	@Autowired
	public JavaMailSender emailSender;
	
	@Autowired
	private UserRepository userRepository;
	
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
		SecurityContextHolder.clearContext();
		sessionStatus.setComplete();
		return "redirect:/";
	}
	
	@GetMapping({"/profile"})
	public String getProfile(Model model) {
		User user = guestUser();
		if (user.getFirstname().matches("guest"))
		{
			return "redirect:/";
		}
		List<Event> events = new ArrayList<Event>();
		try {
		for (Long id : user.getSubscribed())
		{
			events.add(eventRepository.findOne(id));
		}
		model.addAttribute("events", events);
		} catch(NullPointerException exception) {
			
		}
		model.addAttribute("user", user);
		return "profile";
	}
	
	@DeleteMapping({"/profile"})
	public String deleteProfile(SessionStatus sessionStatus) {
		User user = guestUser();
		userRepository.delete(user);
		SecurityContextHolder.clearContext();
		sessionStatus.setComplete();
		return "redirect:/bye";
	}
	
	@PutMapping({"/profile"})
	public String editProfile(Model model, @RequestParam(required = false) String notification) {
		User user = guestUser();
		System.out.println(notification);
		user.setNotification(notification);
		userRepository.save(user);
		return "redirect:/profile";
	}
	
	@GetMapping({"/bye"})
	public String bye() {
		return "bye";
	}
	
	
	
	//might move this to another page because
	// we need to add a time stamp to this that will be very important 
	@PostMapping({"/signup"})
	public String createAccountSubmit(@RequestParam(required = true) String password,
									@RequestParam(required = true) String email,
									@RequestParam(required = true) String firstname,
									@RequestParam(required = true) String lastname,
									@RequestParam(required = true, defaultValue = "false") boolean isAdmin, 
									Model model) throws MessagingException {
		if (userRepository.findByEmail(email) != null)
		{
			model.addAttribute("error", "An account with that email already exists");
			model.addAttribute("firstname", firstname);
			model.addAttribute("lastname", lastname);
			return "createAccount";
		}
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setAdmin(isAdmin);
		user.setVerified(false);
		user.setNotification("none");
		user.setUuid(java.util.UUID.randomUUID().toString());
		
		// send confirmation email
		webSecurityConfig.sendConfirmationEmail(user);
		
		// save the user and add successful account creation attribute
		this.userService.saveUser(user);
		model.addAttribute("login", "Successful account creation! Please check your email and verify your account");
		return "index";
		
	}
	
	@GetMapping({"/confirm"})
	public String confirmEmail(Model model, @RequestParam(required = true) String id) {
		User user = userRepository.findByUuid(id);
		if ( user != null) {
			if (user.isVerified()) {
				model.addAttribute("error", "You have already verified your account");
				return "confirm";
			}
			else {
				user.setVerified(true);
				userRepository.save(user);
				model.addAttribute("error", "You have verified your account");
				return "confirm";
			}
			
		}
		model.addAttribute("error",  "INVALID CONFIRMATION THING");
		return "confirm";
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
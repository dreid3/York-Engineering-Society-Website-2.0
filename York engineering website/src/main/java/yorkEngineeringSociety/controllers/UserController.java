package yorkEngineeringSociety.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import yorkEngineeringSociety.config.MailConfig;
import yorkEngineeringSociety.models.Event;
import yorkEngineeringSociety.models.User;
import yorkEngineeringSociety.repos.EventRepository;
import yorkEngineeringSociety.repos.UserRepository;
import yorkEngineeringSociety.services.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailConfig mailConfig;
	
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
		user.setBlacklist(false);
		user.setNotification("none");
		user.setUuid(java.util.UUID.randomUUID().toString());
		user.setBlacklistid(java.util.UUID.randomUUID().toString());
		user.setResetPassword(false);
		
		// send confirmation email
		mailConfig.sendConfirmationEmail(user);
		
		// save the user and add successful account creation attribute
		this.userService.saveUser(user);
		model.addAttribute("login", "Successful account creation! Please check your email and verify your account");
		return "index";
		
	}
	
	@GetMapping({"/forgotPassword"})
	public String forgotPass() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/";
		}
		
		return "forgotPassword";
	}

	
	@PostMapping({"/forgotPassword"})
	public String forgotPassword(Model model, @RequestParam(required = true) String email) throws MessagingException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/";
		}
		User user = userRepository.findByEmail(email);
		if ( user != null) {
			if (user.isVerified()) {
				user.setUuid(java.util.UUID.randomUUID().toString());
				userRepository.save(user);
				mailConfig.sendForgotPasswordEmail(user);
			}
		}
		model.addAttribute("error",  "IF THE EMAIL EXISTS IT HAS BEEN SENT");
		return "error";
	}
	
	@GetMapping({"/resetPassword"})
	public String resetPass(Model model, @RequestParam(required = true) String id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/";
		}
		User user = userRepository.findByUuid(id);
		if (user == null) {
			model.addAttribute("error", "LINK INVALID");
			return "error";
		}
		
		if (user.isVerified()) {
			user.setResetPassword(true);
			userRepository.save(user);
			model.addAttribute("id", user.getUserId());
			return "resetPassword";
		}
		
		model.addAttribute("error", "You did not confirm your email");
		return "error";
		
	}
	
	@PostMapping({"/resetPassword"})
	public String resetPassword(Model model, @RequestParam(required = true) Long id, @RequestParam(required = true) String password, 
			@RequestParam(required = true) String passwordConfirm, RedirectAttributes redirectAttributes) throws MessagingException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/";
		}
		User user = userRepository.findOne(id);
		if ( user != null) {
			if (user.isResetPassword() && user.isVerified()) {
				if (password.equals(passwordConfirm)) {
					userService.changePasswordEmail(user, password);
					redirectAttributes.addFlashAttribute("error", "You have sucessfully reset your password");
					return "redirect:/";
				}
				else {
					model.addAttribute("id", id);
					model.addAttribute("error", "Your passwords do not match");
					return "resetPassword";
				}
			}
			
		}
		model.addAttribute("error",  "INVALID LINK");
		return "error";
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
				user.setUuid(java.util.UUID.randomUUID().toString());
				userRepository.save(user);
				model.addAttribute("error", "You have verified your account");
				return "confirm";
			}
			
		}
		model.addAttribute("error",  "INVALID CONFIRMATION THING");
		return "confirm";
	}
	
	@GetMapping({"/blacklist"})
	public String blacklist(Model model, @RequestParam(required = true) String id) {
		User user = userRepository.findByBlacklistid(id);
		if ( user != null) {
			if (user.isBlacklist()) {
				model.addAttribute("error", "You have already blocked emails");
				return "error";
			}
			else {
				user.setBlacklist(true);
				userRepository.save(user);
				model.addAttribute("error", "You will no longer recieve emails");
				return "error";
			}
			
		}
		model.addAttribute("error",  "INVALID LINK");
		return "error";
	}
	
	@GetMapping({"/blacklistconfirm"})
	public String blacklistconfirm(Model model, @RequestParam(required = true) String id) {
		model.addAttribute("id", id);
		return "blacklist";
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
		for (Long id : user.getRsvp())
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
		for (Event event : eventRepository.findAll()) {
			if (event.getRsvp() != null) {
				if (event.getRsvp().contains(user.getUserId())) {
					event.getRsvp().remove(user.getUserId());
					eventRepository.save(event);
				}
			}
		}
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
	
	@GetMapping({"/settings"})
	public String profileSettings(Model model) {
		User user = guestUser();
		model.addAttribute("user", user);
		return "settings";
	}
	
	@PutMapping({"/settings"})
	public String saveProfileSettings(Model model, @RequestParam(required = true) String notification
			, @RequestParam(required = true) String firstname, @RequestParam(required = true) String lastname
			, @RequestParam(required = true) String email, @RequestParam(required = false) String currentPassword
			, @RequestParam(required = false) String password) throws MessagingException {
		User user = guestUser();
		
		//password logic, if both aren't null, they want to change their password
		if (!currentPassword.equals("") && !password.equals("")) {
			 if (!userService.changePassword(user, currentPassword, password)) {
				 	System.out.println("this shouldnt be hitting");
					model.addAttribute("error", "The password you entered is incorrect");
					model.addAttribute("user", user);
					return "settings";
			 }
		}
		
		if (!email.equals(user.getEmail())) {
			if (userRepository.findByEmail(email) != null) {
				model.addAttribute("error", "An account with that email already exists");
				model.addAttribute("user", user);
				return "settings";
			} else {
				user.setEmail(email);
				user.setVerified(false);
				//All of this is what needs to happen when you make changes to an email as the authentication changes.
				Collection<SimpleGrantedAuthority> nowAuthorities = 
	            (Collection<SimpleGrantedAuthority>)SecurityContextHolder
	            .getContext().getAuthentication().getAuthorities();
				UsernamePasswordAuthenticationToken authentication = 
			    new UsernamePasswordAuthenticationToken(email, user.getPassword(), nowAuthorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				// send new confirmation email.
				mailConfig.sendConfirmationEmail(user);
			}
		}
		

		
		user.setNotification(notification);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		userRepository.save(user);
		
		return "redirect:/profile";
	}
}

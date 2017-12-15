package yorkEngineeringSociety.controllers;

import javax.mail.MessagingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import yorkEngineeringSociety.config.MailConfig;
import yorkEngineeringSociety.models.Event;
import yorkEngineeringSociety.models.Newsletter;
import yorkEngineeringSociety.models.User;
import yorkEngineeringSociety.repos.EventRepository;
import yorkEngineeringSociety.repos.NewsletterRepository;
import yorkEngineeringSociety.repos.UserRepository;
import yorkEngineeringSociety.services.UserService;
import yorkEngineeringSociety.repos.MemberRepository;
import yorkEngineeringSociety.models.Member;



@Controller
public class AdminController {
	
	@Autowired
	MailConfig mailConfig;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRepository memberRepository;
	
	@Autowired
	private NewsletterRepository newsletterRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
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
	
	@GetMapping({"/admin"})
	public String adminPage(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "admin";
	}
	
	@PostMapping({"/admin/massEmail"})
	public String massEmail(RedirectAttributes redirectAttributes, @RequestParam(required = true) String subject
			, @RequestParam(required = true) String editval) throws MessagingException {
		mailConfig.massEmail(editval, subject);
		redirectAttributes.addFlashAttribute("error", "You have successfully sent an email to everyone");
		return "redirect:/admin";
	}
	
	@DeleteMapping({"/admin/delete/{userId}"})
	public String deleteUser(@PathVariable(required = false) long userId, @RequestParam(required = false) String email, RedirectAttributes redirectAttributes) {
		
		// they supplied id 
		
		 if (email != null)	{
			User user = userRepository.findByEmail(email);
			// user doesnt exist with email
			if (user == null) {
				redirectAttributes.addFlashAttribute("error", "User with that email does not exist");
				return "redirect:/admin";
			}
			
			// delete that user
			for (Event event : eventRepository.findAll()) {
				if (event.getRsvp() != null) {
					if (event.getRsvp().contains(user.getUserId())) {
						event.getRsvp().remove(user.getUserId());
						eventRepository.save(event);
					}
				}
			}
			userRepository.delete(user);
			redirectAttributes.addFlashAttribute("error", "User sucessfully deleted");
			return "redirect:/admin";
			
			
		}
		 
		 else if (userId != 0L) {
				userRepository.delete(userId);
				redirectAttributes.addFlashAttribute("error", "User sucessfully deleted");
				return "redirect:/admin";
			}
		return "redirect:/admin";
	}
	
	@DeleteMapping({"/admin/deleteEvent/{eventId}"})
	public String deleteEvent(@PathVariable(required = true) long eventId, RedirectAttributes redirectAttributes) {
		
		if (eventRepository.exists(eventId)) {
			
			Event event = eventRepository.findOne(eventId);
			eventRepository.delete(eventId);
			redirectAttributes.addFlashAttribute("error", "Event sucessfully deleted");
			return "redirect:/events";
		}
		redirectAttributes.addFlashAttribute("error", "Event does not exist");
			
		return "redirect:/events";
	}
	
	@DeleteMapping({"/admin/deleteMember/{memberId}"})
	public String deleteMember(@PathVariable(required = true) long memberId, RedirectAttributes redirectAttributes) {
		
		if (memberRepository.exists(memberId)) {
			memberRepository.delete(memberId);
			redirectAttributes.addFlashAttribute("error", "Member sucessfully deleted");
			return "redirect:/member";
		}
		redirectAttributes.addFlashAttribute("error", "Member does not exist");
			
		return "redirect:/member";
	}
	
	
	@DeleteMapping({"/admin/deleteNewsletter/{newsletterId}"})
	public String deleteNewsletter(@PathVariable(required = true) long newsletterId, RedirectAttributes redirectAttributes) {
		
		if (newsletterRepository.exists(newsletterId)) {
			newsletterRepository.delete(newsletterId);
			redirectAttributes.addFlashAttribute("error", "Newsletter sucessfully deleted");
			return "redirect:/newsletters";
		}
		redirectAttributes.addFlashAttribute("error", "Newsletter does not exist");
			
		return "redirect:/newsletters";
	}
	
	@PostMapping({"/admin/signup"})
	public String createAccountSubmit(@RequestParam(required = true) String password,
									@RequestParam(required = true) String email,
									@RequestParam(required = true) String firstname,
									@RequestParam(required = true) String lastname,
									@RequestParam(required = true, defaultValue = "false") boolean isAdmin, 
									RedirectAttributes redirectAttributes) throws MessagingException {
		if (userRepository.findByEmail(email) != null)
		{
			redirectAttributes.addFlashAttribute("error", "An account with that email already exists");
			redirectAttributes.addFlashAttribute("firstname", firstname);
			redirectAttributes.addFlashAttribute("lastname", lastname);
			return "admin";
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
		user.setBlacklistid(java.util.UUID.randomUUID().toString());
		user.setResetPassword(false);
		
		// send confirmation email
		mailConfig.sendConfirmationEmail(user);
		
		// save the user and add successful account creation attribute
		this.userService.saveUser(user);
		redirectAttributes.addFlashAttribute("error", "You have successfully created the user account");
		return "redirect:/admin";
		
	}
	
	@PostMapping({"/admin/createEvent"})
	public String createEvent(Model model, @RequestParam String editval,
			@RequestParam String name,  @RequestParam String address, @RequestParam String date) {
		Event event = new Event();
		event.setName(name);
		event.setAddress(address);
		event.setTemplate(editval);
		DateFormat df = new SimpleDateFormat("MM/d/yy h:mm a");
		Date dateobj = new Date();
		try {
			dateobj = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(date + "endshere");
		System.out.println(editval);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateobj);
		event.setCalendar(calendar);
		eventRepository.save(event);
		
		return "/admin";
	}
	
	@PostMapping({"/admin/createNesletter"})
	public String createNewsletter(Model model, @RequestParam String editval,
			@RequestParam String name, @RequestParam String date) {
		Newsletter newsletter = new Newsletter();
		newsletter.setName(name);
		newsletter.setTemplate(editval);
		DateFormat df = new SimpleDateFormat("MM/d/yy h:mm a");
		Date dateobj = new Date();
		try {
			dateobj = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(date + "endshere");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateobj);
		newsletter.setCalendar(calendar);
		newsletterRepository.save(newsletter);
		return "/admin";
	}
}

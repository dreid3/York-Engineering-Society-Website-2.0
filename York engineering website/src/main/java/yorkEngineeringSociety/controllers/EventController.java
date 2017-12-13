package yorkEngineeringSociety.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import yorkEngineeringSociety.config.MailConfig;
import yorkEngineeringSociety.config.WebSecurityConfig;
import yorkEngineeringSociety.models.Event;
import yorkEngineeringSociety.models.User;
import yorkEngineeringSociety.repos.EventRepository;
import yorkEngineeringSociety.repos.UserRepository;
import yorkEngineeringSociety.services.UserService;

@Controller
public class EventController {
	
	@Autowired
	public JavaMailSender emailSender;
	
	@Autowired
	public MailConfig mailConfig;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@ModelAttribute("df")
	public DateFormat dateFormat() {
		DateFormat df = new SimpleDateFormat("MM/d/yy h:mm a");
		return df;
	}
	@ModelAttribute("admin")
	public String isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean hasUserRole = authentication.getAuthorities().stream()
		          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
		if (hasUserRole)
		{
			return "admin";
		}
		return "user";
		
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
	
	@GetMapping({"/createEvent"})
	public String eventCreate(Model model) {
		return "createEvent";
	}
	
	@PostMapping({"/createEvent"})
	public String eventSave(Model model, @RequestParam String editval,
			@RequestParam String name,  @RequestParam String address, @RequestParam String date, @RequestParam(defaultValue="") String notifyDate) {
		Event event = new Event();
		event.setName(name);
		
		//set an invalid key check here for the name 
		/*for(int i = 0; i < name.length(); i++) {
			if(name.charAt(i) == '[' || name.charAt(i) == ']'
					|| name.charAt(i) == '(' || name.charAt(i) == ')' 
					|| name.charAt(i) == '}' || name.charAt(i) == '{' 
					|| name.charAt(i) == '/' || name.charAt(i) == '.'
					|| name.charAt(i) == ',' || name.charAt(i) == '<'
					|| name.charAt(i) == '>' || name.charAt(i) == '~') {
				System.out.println("There was an invalid character in the news letter name");
				@RequestParam String newName; 
				event.setName(newName);
			}
		}*/
		
		event.setAddress(address);
		event.setTemplate(editval);
		DateFormat df = new SimpleDateFormat("MM/d/yy h:mm a");
		Date dateobj = new Date();
		Date notifyobj = new Date();
		try {
			dateobj = df.parse(date);
			if (!notifyDate.equals("")) {
			notifyobj = df.parse(notifyDate);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateobj);
		event.setCalendar(calendar);
		event.setSubreminder(false);
	
		if (!notifyDate.equals("")) {
		event.setReminder(false);
		Calendar calendarTwo = Calendar.getInstance();
		calendarTwo.setTime(notifyobj);
		event.setReminderDate(calendarTwo);
		} else {
			event.setReminder(true);
		}
		eventRepository.save(event);
		return "redirect:/events";
		
	}
	
	@GetMapping({"/events/{eventId}"})
	public String singleEvent(Model model, @PathVariable long eventId) {
		model.addAttribute("event", eventRepository.findOne(eventId));
		return "eventPage";
	}
	
	@GetMapping({"/events/{eventId}/editEvent"})
	public String editEventPage(Model model, @PathVariable long eventId) {
		model.addAttribute("event", eventRepository.findOne(eventId));
		return "editEvent";
	}
	
	@GetMapping({"/events/{eventId}/subscribe"})
	public String subscribeEvent(Model model, @PathVariable long eventId) throws MessagingException {
		int eventCounter = 0; 
		
		Event event = eventRepository.findOne(eventId);
		User user = guestUser();
		if (user.getFirstname().matches("guest"))
		{
			return "redirect:/events/" + eventId;
		}
		if (!user.isVerified())
		{
			model.addAttribute("error", "You must verify your account first before you RSVP.");
			model.addAttribute("event", eventRepository.findOne(eventId));
			return "eventPage";
		}
		
		// if a user has never subscribed before, it will be null so create it
		if(user.getRsvp() == null) {
			ArrayList<Long> rsvp = new ArrayList<Long>(); 
			user.setRsvp(rsvp);
		}
		
		if (user.getRsvp().contains(eventId)) {
		model.addAttribute("error", "You are already RSVP'd to this event");
		model.addAttribute("event", eventRepository.findOne(eventId));
		return "eventPage";
		}
		
		if (event.getRSVP() == null)
		{
			ArrayList<Long> rsvp = new ArrayList<Long>();
			event.setRsvp(rsvp);
			eventCounter++; 
		}
		
		event.getRSVP().add(user.getUserId());
		
		userRepository.save(user);                                                      
		eventRepository.save(event);


		return "redirect:/events/" + eventId;
	}
	
	
	@PostMapping({"/events/{eventId}/editEvent"})
	public String editEvent(Model model, @PathVariable long eventId, @RequestParam String editval,
			@RequestParam String name,  @RequestParam String address, @RequestParam String date) {
		Event event = eventRepository.findOne(eventId);
		event.setAddress(address);
		event.setName(name);
		event.setTemplate(editval);
		DateFormat df = new SimpleDateFormat("MM/d/yy h:mm a");
		Date dateobj = new Date();
		try {
			dateobj = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateobj);
		event.setCalendar(calendar);
		eventRepository.save(event);
		model.addAttribute("event", event);
		return "eventPage";
	}
	
	
}

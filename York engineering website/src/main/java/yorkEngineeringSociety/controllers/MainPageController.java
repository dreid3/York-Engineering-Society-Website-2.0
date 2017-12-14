package yorkEngineeringSociety.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

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

// Realistically this class should just be the controller for simple links from the main page
@Controller
public class MainPageController {
	@Autowired
	private UserService userService;
	
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
		System.out.println(user.isAdmin());
		return user;
	}

	@GetMapping({"/"})
	public String home(Model model) {
		// fix for possible null objects, comment it out when fixed
		for (Event event : eventRepository.findAll()) {
			if (event.getRsvp() == null) {
				ArrayList<Long> rsvp = new ArrayList<Long>();
				event.setRsvp(rsvp);
				event.setRsvpCount(0);
				eventRepository.save(event);
			}
		}
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
	
	
	
	@GetMapping({"/bye"})
	public String bye() {
		return "bye";
	}
	
	@GetMapping({"calendar"})
	public String calendar() {
		
		

		return "calendar";
	}
	
	@PostMapping({"/calendar"})
	public String eventSearch(Model model, @RequestParam String date) {
		List<Event> eventsList = eventRepository.findAll();
		List<Event> eventDate = new ArrayList<Event>();
		DateFormat df = new SimpleDateFormat("MM/d/yy");
		Date dateobj = new Date();
		
		try {
			dateobj = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println(date + "endshere");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateobj);
		//event.setCalendar(calendar);
		//eventRepository.save(event);
		
		for(Event event: eventsList) {
			Calendar cal = event.getCalendar();
			boolean sameDay = cal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && cal.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && cal.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH);
			
			System.out.println(sameDay);
			
			if(sameDay == true) {
				eventDate.add(event);
				//model.addAttribute("events", event);
			}
			
		}
		
		model.addAttribute("events", eventDate);
		
		// compare events list dates to string date
		
		// any that match put into events list
				
		// return list of events with this date
		//return events;
		// if no events match arg0 date then return null
		
		return "/calendar";
		
	}
	
	@GetMapping({"contact"})
	public String contact() {
	/*	var CALENDAR = function () { 
		    var wrap, label,  
		            months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]; 
	 
		    function init(newWrap) { 
		    	wrap     = $(newWrap || "#cal"); 
		    	label    = wrap.find("#label"); 
		    	wrap.find("#prev").bind("click.calendar", function () { switchMonth(false); }); 
		    	wrap.find("#next").bind("click.calendar", function () { switchMonth(true);  }); 
		    	label.bind("click", function () { switchMonth(null, new Date().getMonth(), new Date().getFullYear()); });        
		    	label.click();
		    } 
	 
		    function switchMonth(next, month, year) { 
		    	var curr = label.text().trim().split(" "), calendar, tempYear =  parseInt(curr[1], 10); 
		    	month = month || ((next) ? ( (curr[0] === "December") ? 0 : months.indexOf(curr[0]) + 1 ) : ( (curr[0] === "January") ? 11 : months.indexOf(curr[0]) - 1 )); 
		    	year = year || ((next && month === 0) ? tempYear + 1 : (!next && month === 11);
		    	
		    	if (!month) { 
		    	    if (next) { 
		    	        if (curr[0] === "December") { 
		    	            month = 0; 
		    	        } else { 
		    	            month = months.indexOf(curr[0]) + 1; 
		    	        } 
		    	    } else { 
		    	        if (curr[0] === "January") { 
		    	            month = 11; 
		    	        } else { 
		    	            month = months.indexOf(curr[0]) - 1; 
		    	        } 
		    	    } 
		    	}
		    	calendar =  createCal(year, month); 
		        $("#cal-frame", wrap) 
		            .find(".curr") 
		                .removeClass("curr") 
		                .addClass("temp") 
		            .end() 
		            .prepend(calendar.calendar()) 
		            .find(".temp") 
		                .fadeOut("slow", function () { $(this).remove(); }); 
	 
		        $('#label').text(calendar.label);
		        
		    } 
	 
		    function createCal(year, month) { 
		    	var day = 1, i, j, haveDays = true,  
		    	        startDay = new Date(year, month, day).getDay(), 
		    	        daysInMonths = [31, (((year%4==0)&&(year%100!=0))||(year%400==0)) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31], 
		    	        calendar = [];
		    	
		    	if (createCal.cache[year]) { 
		    	    if (createCal.cache[year][month]) { 
		    	        return createCal.cache[year][month]; 
		    	    } 
		    	} else { 
		    	    createCal.cache[year] = {}; 
		    	}
		    	
		    	i = 0; 
		    	while (haveDays) { 
		    	    calendar[i] = []; 
		    	    for (j = 0; j < 7; j++) { 
		    	        if (i === 0) { 
		    	            if (j === startDay) { 
		    	                calendar[i][j] = day++; 
		    	                startDay++; 
		    	            } 
		    	        } else if (day <= daysInMonths[month]) { 
		    	            calendar[i][j] = day++; 
		    	        } else { 
		    	            calendar[i][j] = ""; 
		    	            haveDays = false; 
		    	        } 
		    	        if (day > daysInMonths[month]) { 
		    	            haveDays = false; 
		    	        } 
		    	    } 
		    	    i++; 
		    	}
		    	
		    	if (calendar[5]) { 
		    	    for (i = 0; i < calendar[5].length; i++) { 
		    	        if (calendar[5][i] !== "") { 
		    	            calendar[4][i] = "<span>" + calendar[4][i] + "</span><span>" + calendar[5][i] + "</span>"; 
		    	        } 
		    	    } 
		    	    calendar = calendar.slice(0, 5); 
		    	}
	 
		    } 
		    createCal.cache = {}; 
		    return { 
		        init : init, 
		        switchMonth : switchMonth, 
		        createCal   : createCal 
		    }; 
		    
		    for (i = 0; i < calendar.length; i++) { 
			    calendar[i] = "<tr><td>" + calendar[i].join("</td><td>") + "</td></tr>"; 
			} 
			calendar = $("<table>" + calendar.join("") + "</table>").addClass("curr"); 
		 
			$("td:empty", calendar).addClass("nil"); 
			if (month === new Date().getMonth()) { 
			    $('td', calendar).filter(function () { return $(this).text() === new Date().getDate().toString(); }).addClass("today"); 
			} 
			createCal.cache[year][month] = { calendar : function () { return calendar.clone() }, label : months[month] + " " + year }; 
		 
			return createCal.cache[year][month];
		};*/

		return "contact";
	}
	
	@GetMapping({"/events"})
	public String events(Model model) {
		User user = guestUser();
		if (user.isAdmin()) {
			model.addAttribute("admin", "admin");
		}
		model.addAttribute("events", eventRepository.findAll());
		return "events";
	}
	
	


}
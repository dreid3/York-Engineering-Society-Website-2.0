package yorkEngineeringSociety.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yorkEngineeringSociety.models.Newsletter;
import yorkEngineeringSociety.models.User;
import yorkEngineeringSociety.repos.NewsletterRepository;
import yorkEngineeringSociety.services.UserService;

@Controller
public class NewsletterController {
	@Autowired
	private NewsletterRepository newsletterRepository;
	
	@Autowired
	private UserService userService;
	
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
	
	@GetMapping({"/createNewsletter"})
	public String newsletterCreate(Model model) {
		return "createnewsletter";
	}
	
	@PostMapping({"/createNewsletter"})
	public String newsletterSave(Model model, @RequestParam String editval,
			@RequestParam String name, @RequestParam int year,
			@RequestParam int month, @RequestParam int day) {
		Newsletter newsletter = new Newsletter();
		newsletter.setName(name);
		newsletter.setDate(year, month, day);
		newsletter.setTemplate(editval);
		newsletterRepository.save(newsletter);
		return "redirect:/newsletters";
	}
	
	@GetMapping({"/newsletters"})
	public String newsletters(Model model) {
		model.addAttribute("newsletters", newsletterRepository.findAll());
		return "newsletters";
	}
	
	@GetMapping({"/newsletters/{newsletterId}"})
	public String singlenewsletter(Model model, @PathVariable long newsletterId) {
		model.addAttribute("newsletter", newsletterRepository.findOne(newsletterId));
		return "newsletterPage";
	}
	
	@GetMapping({"/newsletters/{newsletterId}/editnewsletter"})
	public String editnewsletterPage(Model model, @PathVariable long newsletterId) {
		model.addAttribute("newsletter", newsletterRepository.findOne(newsletterId));
		return "editnewsletter";
	}
	
	@PostMapping({"/newsletters/{newsletterId}/editnewsletter"})
	public String editnewsletter(Model model, @PathVariable long newsletterId, @RequestParam String editval,
			@RequestParam String name, @RequestParam int year, @RequestParam int month, @RequestParam int day) {
		Newsletter newsletter = newsletterRepository.findOne(newsletterId);
		newsletter.setName(name);
		newsletter.setTemplate(editval);
		newsletter.setDate(year,  month, day);
		newsletterRepository.save(newsletter);
		model.addAttribute("newsletter", newsletter);
		return "newsletterPage";
	}
}

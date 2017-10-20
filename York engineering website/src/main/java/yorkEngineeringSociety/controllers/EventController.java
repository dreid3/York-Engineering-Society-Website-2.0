package yorkEngineeringSociety.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import yorkEngineeringSociety.models.Event;
import yorkEngineeringSociety.repos.EventRepository;

@Controller
public class EventController {
	
	@Autowired
	private EventRepository eventRepository;
	
	@GetMapping({"/createEvent"})
	public String eventCreate(Model model) {
		return "createEvent";
	}
	
	@PostMapping({"/createEvent"})
	public String eventSave(Model model, @RequestParam String editval) {
		Event event = new Event("address", editval, "anothername");
		eventRepository.save(event);
		return "redirect:/Events";
	}
	
	@GetMapping({"/Events"})
	public String events(Model model) {
		model.addAttribute("events", eventRepository.findAll());
		return "events";
	}
}

package yorkEngineeringSociety.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yorkEngineeringSociety.models.Event;
import yorkEngineeringSociety.repos.EventRepository;
import yorkEngineeringSociety.services.EventService;

@Service("eventService")
public class EventServerlmpl implements EventService {
	@Autowired
	private EventRepository eventRepository;
	
	//@Autowired
	
	@Override
	public Event findByName(String name) {
		return this.eventRepository.findByName(name);
	}
	
	@Override
	public Event findEventByDay(String day) {
		return this.eventRepository.findEventByDay(day);
	}
	
	@Override
	public Event findEventByMonth(String month) {
		return this.eventRepository.findEventByMonth(month);
	}
	
	@Override
	public void changeEventDay(Event event, String day) {
		event.setDay(day);
		this.eventRepository.save(event); 
	}
	
	@Override
	public void changeEventMonth(Event event, String month) {
		event.setMonth(month);
		this.eventRepository.save(event);
	}
	
	@Override
	public void changeEventAddress(Event event, String address) {
		event.setAddress(address);
		this.eventRepository.save(event);
	}

}

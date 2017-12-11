package yorkEngineeringSociety.services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yorkEngineeringSociety.models.Event;
import yorkEngineeringSociety.repos.EventRepository;
import yorkEngineeringSociety.services.EventService;

@Service("eventService")
public class EventServerImpl implements EventService {
	@Autowired
	private EventRepository eventRepository;
	
	//@Autowired
	
	
	@Override
	public void changeEventAddress(Event event, String address) {
		event.setAddress(address);
		this.eventRepository.save(event);
	}

	@Override
	public List<Event> getEventsOrderedByDate() {
		List<Event> events = eventRepository.findAll();
		
		Collections.sort(events, new Comparator<Event> () {
			public int compare(Event one, Event two) {
				return one.getCalendar().compareTo(two.getCalendar());
			}
		});
		return events;
	}

	//@Override
	public Event findByDate(Calendar c) {
		// TODO Auto-generated method stub
		return findByDate(c.DAY_OF_WEEK);
	}

	
	
	//need a void test here 

}

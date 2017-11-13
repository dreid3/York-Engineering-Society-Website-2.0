package yorkEngineeringSociety.services;

import java.util.Calendar;
import java.util.List;

import yorkEngineeringSociety.models.Event;


public interface EventService {
	void changeEventAddress(Event event, String arg0); 
	List<Event> getEventsOrderedByDate();
	
	//need a find event by date 
	Event findByDate(Calendar arg0);
	
}

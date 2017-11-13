package yorkEngineeringSociety.services;

import java.util.Calendar;
import java.util.List;

import yorkEngineeringSociety.models.Event;


public interface EventService {
	Event findByName(String arg0);
	void changeEventAddress(Event event, String arg0); 
	void changeEventDate(Event event, int arg0, int arg1, int arg2, int arg3, int arg4);
	List<Event> getEventsOrderedByDate();
	
	//need a find event by date 
	Event findByDate(Calendar arg0);
	
}

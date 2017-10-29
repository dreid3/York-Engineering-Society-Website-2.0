package yorkEngineeringSociety.services;

import yorkEngineeringSociety.models.Event;


public interface EventService {
	Event findByName(String arg0);
	Event findEventByDay(String arg0);
	Event findEventByMonth(String arg0); 
	
	void changeEventDay(Event event, String arg0);
	void changeEventMonth(Event event, String month);
	void changeEventAddress(Event event, String arg0); 
}

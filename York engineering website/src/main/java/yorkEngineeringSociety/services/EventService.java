package yorkEngineeringSociety.services;

import yorkEngineeringSociety.models.Event;


public interface EventService {
	Event findByName(String arg0);
	Event findByID(int arg0);
	Event findEventByDay(String arg0);
	Event findEventByMonth(String arg0); 
	
	void saveEvent(Event arg0); 
	void changeEventDay(Event event, String arg0);
	void changeEventMonth(Event event, String arg0);
	void changeEventAddress(Event event, String arg0); 
}

package yorkEngineeringSociety.services;

import yorkEngineeringSociety.models.Event;


public interface EventService {
	Event findByName(String arg0);
	void changeEventAddress(Event event, String arg0); 
}

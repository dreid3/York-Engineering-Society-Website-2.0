package yorkEngineeringSociety.services;

import yorkEngineeringSociety.models.Event;

public interface EventService {
	Event findEventByDay(String arg0); 
	Event findEventByMonth(String arg0);
	Event findEventByYear(String arg0); 
	
	//update methods go here as well 
}

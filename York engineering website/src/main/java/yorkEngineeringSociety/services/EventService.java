package yorkEngineeringSociety.services;

import yorkEngineeringSociety.models.Event;

public interface EventService {
	Event findByName(String name);
	
}

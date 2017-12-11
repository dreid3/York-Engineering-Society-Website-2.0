package serviceTests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test; 

import yorkEngineeringSociety.models.Event;
import yorkEngineeringSociety.services.EventServerImpl;

public class EventServiceTests {
	private EventServerImpl events;
	
	private Event event1; 
	private Event event2; 
	private Event nullEvent; 
	
	@Before
	public void setUp() {
		
		events = new EventServerImpl(); 
		
		event1 = new Event(); 
		event2 = new Event(); 
		nullEvent = new Event(); 
		
		//test event 1
		event1.setAddress("Spring Garden 312");
		event1.setName("System Testing");
		event1.setDescription("Get this sh*t working");
		
		event1.setDate(2017, 11, 23, 12, 30);
		
		event2.setAddress("441 Country Club Road");
		event2.setName("Presentation");
		event2.setDescription("To present our work");
		
		event2.setDate(2017, 12, 15, 12, 45);
	}
	
	
	@Test
	public void TestChangeEventAddressTest() {
		String newAddress = "Kinsley";
		events.changeEventAddress(event1, newAddress);
		
		assertEquals("Kinsley", event1.getAddress()); 
	}
	
	//get events ordered by date 
	@Test
	public void TestGetOrderedByDate() {
		
		boolean check = true;
		List<Event> eventList = events.getEventsOrderedByDate(); 
		
		Event e1 = eventList.get(0); 
		Event e2; 
		e1.getCalendar();
		
		for(int i = 1; i < eventList.size(); i++) {
			e2 = eventList.get(i);
			e2.getCalendar();
		
			if(Calendar.DAY_OF_YEAR > Calendar.DAY_OF_YEAR) {
				check = false; 
			}
		}
		assertTrue(check); 
	}
	
}

package serviceTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test; 

import yorkEngineeringSociety.models.Event;
import yorkEngineeringSociety.services.EventServerImpl;

public class EventServiceTests {
	EventServerImpl events;
	
	Event event1; 
	Event event2; 
	Event nullEvent; 
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
	public void TestSearchByName() {
		assertEquals("System Testing", events.findByName(event1.getName()));
		assertEquals("Presentation", events.findByName(event2.getName())); 
	}
	
	
	
	public void TestChangeEventDate() {
		int year = 2018; 
		int month = 7; 
		int day = 20; 
		int hour = 12; 
		int min = 30; 
		events.changeEventDate(event1, year, month, day, hour, min);
		
		assertEquals(year, event1.getCalendar().get(year));
		assertEquals(month, event1.getCalendar().get(month));
		assertEquals(day, event1.getCalendar().get(day));
		assertEquals(hour, event1.getCalendar().get(hour));
		assertEquals(min, event1.getCalendar().get(min)); 
	}
	
	@Test
	public void TestChangeEventAddressTest() {
		String newAddress = "Kinsley";
		events.changeEventAddress(event1, newAddress);
		
		assertEquals("Kinsley", event1.getAddress()); 
	}
	
	//get events ordered by date 
	
	
}

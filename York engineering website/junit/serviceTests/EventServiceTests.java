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
		event1.setDay("7");
		event1.setMonth("November");
		event1.setYear("2017"); 
		event1.setTime("11:00PM"); 
	}
	
	public void TestSearchByDay() {
		assertEquals("7", events.findEventByDay(event1.getDay()));
	}
	
	public void TestSearchByMonth() {
		assertEquals("November", events.findEventByMonth(event1.getMonth()));
	}
	
	public void TestSearchByName() {
		assertEquals("System Testing", events.findByName(event1.getName()));
	}
	
	public void TestChangeEventDay() {
		String newDay = "9";
		events.changeEventDay(event1, newDay);
		
		assertEquals("9", event1.getDay()); 
	}
	
	public void TestChangeEventMonth() {
		String newMonth = "December";
		events.changeEventMonth(event1, newMonth);
		
		assertEquals("December", event1.getMonth()); 
	}
	
	public void TestChangeEventAddressTest() {
		String newAddress = "Kinsley";
		events.changeEventAddress(event1, newAddress);
		
		assertEquals("Kinsley", event1.getAddress()); 
	}
}

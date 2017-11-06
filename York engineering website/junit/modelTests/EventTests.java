package modelTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import yorkEngineeringSociety.models.Event;

public class EventTests {

	public Event event1; 
	public Event event2; 
	public Event event3;
	
	@Before
	public void setUp() {
		event1 = new Event();
		
		event1.setAddress("441 Country Club Road");
		event1.setDescription("Studying, eating, sleeping, repeating.");
		event1.setName("College");

		event1.setDate(2017, 11, 7, 17, 30);
		
		event2 = new Event(); 
		event2.setAddress("48 N Beaver Street"); 
		event2.setDescription("Happy Hour");
		event2.setName("Recreation"); 
		
		event2.setDate(2018, 3, 12, 18, 0);
		
		event3 = new Event();
		event3.setAddress("");
		event3.setName("");
		
	}
	
	
	@Test
	public void testAddress() {
		String address1 = event1.getAddress();
		String address2 = event2.getAddress(); 
		assertEquals("441 Country Club Road", address1);
		assertEquals("48 N Beaver Street", address2); 
	}
	
	@Test
	public void testDescription() {
		String description1 = event1.getDescription();
		String desc2 = event2.getDescription(); 
		assertEquals("Studying, eating, sleeping, repeating.", description1);
		assertEquals("Happy Hour", desc2); 
	}
	
	@Test
	public void testTitle() {
		String title1 = event1.getName(); 
		String title2 = event2.getName();
		
		assertEquals("College", title1);
		assertEquals("Recreation", title2); 
	}
	
	
	public void testdate() {
		 
		
		
	
	}
	
	//test that empty values are present in the event 
	public void testEmpty() {
		
		String a = event3.getAddress(); 
		String n = event3.getName(); 
		
		
	}
	
	
	
	
}

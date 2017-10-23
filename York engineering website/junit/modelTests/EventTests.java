package modelTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import yorkEngineeringSociety.models.Event;

public class EventTests {

	public Event event1; 
	public Event event2; 
	
	@Before
	public void setUp() {
		event1 = new Event();
		
		event1.setAddress("441 Country Club Road");
		event1.setDescription("Studying, eating, sleeping, repeating.");
		event1.setName("College");
		event1.setDay("Monday");
		event1.setMonth("October"); 
		event1.setYear("2017"); 
		
		event2 = new Event(); 
		event2.setAddress("48 N Beaver Street"); 
		event2.setDescription("Happy Hour");
		event2.setName("Recreation"); 
		event2.setDay("22nd"); 
		event2.setMonth("November");
		event2.setYear("2017"); 
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
	
	@Test
	public void testdate() {
		String d = event1.getDay(); 
		String m = event1.getMonth();
		String y = event1.getYear(); 
		
		String d2 = event2.getDay();
		String m2 = event2.getMonth();
		String y2 = event2.getYear(); 
		
		assertEquals(d, "Monday");
		assertEquals(m, "October");
		assertEquals(y, "2017"); 
		
		assertEquals(d2, "22nd");
		assertEquals(m2, "November");
		assertEquals(y2, "2017"); 
	
	}
	
	
	
	
}

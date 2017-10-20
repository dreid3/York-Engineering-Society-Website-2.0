package modelTests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import yorkEngineeringSociety.models.Event;

public class EventTests {

	public Event event1; 
	
	@Before
	public void setUp() {
		event1 = new Event();
		
		event1.setAddress("441 Country Club Road");
		event1.setDescription("Studying, eating, sleeping, repeating.");
		event1.setTitle("College");
		
		
	}
	
	@Test
	public void testAddress() {
		String address1 = event1.getAddress();
		
		assertEquals("441 Country Club Road", address1);
	}
	
	@Test
	public void testDescription() {
		String description1 = event1.getDescription();
		
		assertEquals("Studying, eating, sleeping, repeating.", description1);
	}
	
	@Test
	public void testTitle() {
		String title1 = event1.getTitle();
		
		assertEquals("College", title1);
	}
	
	
	
	
}

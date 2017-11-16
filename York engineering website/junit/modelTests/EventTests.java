package modelTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import yorkEngineeringSociety.models.Event;
import yorkEngineeringSociety.models.Newsletter;

public class EventTests {

	public Event event1; 
	public Event event2; 
	public Event event3;
	
	private List<Event> eventList; 
	@Before
	public void setUp() {
		
		eventList = new ArrayList<Event>(); 
		
		event1 = new Event();
		
		event1.setAddress("441 Country Club Road");
		event1.setDescription("Studying, eating, sleeping, repeating.");
		event1.setName("College");

		//event1.setDate(2017, 11, 7, 17, 30);
		
		event2 = new Event(); 
		event2.setAddress("48 N Beaver Street"); 
		event2.setDescription("Happy Hour");
		event2.setName("Recreation"); 
		
		//event2.setDate(2018, 3, 12, 18, 0);
		
		event3 = new Event();
		event3.setAddress("");
		event3.setName("/LetsGet RightintoTheNewz>");
		
		eventList.add(event1);
		eventList.add(event2);
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
	
	public void testInvalidEventName() {
		boolean validName = true; 
		//itterate through the list of names we created
		String name = event3.getName();
		for(int i = 0; i < name.length(); i++) {
			if(name.charAt(i) == '[' || name.charAt(i) == ']'
					|| name.charAt(i) == '(' || name.charAt(i) == ')' 
					|| name.charAt(i) == '}' || name.charAt(i) == '{' 
					|| name.charAt(i) == '/' || name.charAt(i) == '.'
					|| name.charAt(i) == ',' || name.charAt(i) == '<'
					|| name.charAt(i) == '>' || name.charAt(i) == '~') {
				System.out.println("There was an invalid character in the news letter name");
				validName = false; 
			}
		}
		
		assertFalse(validName);

	}

	
	
	
}

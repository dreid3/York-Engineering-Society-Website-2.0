package serviceTests;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test; 

import yorkEngineeringSociety.models.Newsletter; 
import yorkEngineeringSociety.services.NewsletterServerImpl;

public class NewsletterServiceTests {
	
	private NewsletterServerImpl newsletters; 
	private Newsletter n; 
	private Newsletter n2; 
	private Newsletter nulLetter; 
	
	public void setUp() {
		newsletters = new NewsletterServerImpl(); 
		
		
		n = new Newsletter(); 
		n2 = new Newsletter(); 
		nulLetter = new Newsletter();
		
		n.setName("Welcome");
		n.setNewsletterId(1);
		n.setTemplate("Introductory post");
		n.setDate(2017, 12);
		
		n2.setName("New Year");
		n2.setNewsletterId(2);
		n2.setTemplate("New Years post");
		n2.setDate(2018, 1);
		
		nulLetter = new Newsletter(); 
		nulLetter.setName("");
		nulLetter.setTemplate("");
		nulLetter.setDate(0, 0);
	}
	
	@Test
	public void testFindNewsletterByDate() {
		n2.getCalendar();
		
		int day = Calendar.DAY_OF_WEEK; 
		
		Calendar c = n2.getCalendar();
		if(c.DAY_OF_WEEK == 0) {  
			fail(); 
		}
		else {
			n.getCalendar();
			assertEquals(Calendar.DAY_OF_WEEK, day);
		}
	}	
	
	@Test
	public void testFindNewsletterByName() {
		String name1 = "Welcome";
		String name2 = "New Year"; 
		
		assertEquals(n, newsletters.findByName(name1));
		assertEquals(n2, newsletters.findByName(name2)); 
	}
	
	@Test
	public void testChangeTempalte() {
		String template1 = "welcome back";
		String template2 = "Valentines";
		
		newsletters.changeNewsletterTemplate(n, template1);
		newsletters.changeNewsletterTemplate(n2, template2);
		
		assertEquals("welcome back", n.getTemplate()); 
		assertEquals("Valentinees", n2.getTemplate()); 
	}
	
	public void testNulls() {
		
	}
}

package serviceTests;

import static org.junit.Assert.*;
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
		
		
	}
<<<<<<< HEAD
	
	@Test
=======
	/*
>>>>>>> branch 'master' of https://github.com/dreid3/York-Engineering-Society-Website-2.0.git
	public void testSearchByName() {
		assertEquals(n, newsletters.findByName("Welcome")); 
		assertEquals(n2, newsletters.findByName("New Year"));
	}
<<<<<<< HEAD
	
=======
	*/
>>>>>>> branch 'master' of https://github.com/dreid3/York-Engineering-Society-Website-2.0.git
	/*
	public void testSearchByID() {
		long id1 = 1; 
		long id2 = 2; 
		assertEquals(id1, newsletters.findByID(n.getNewsletterId())); 
		assertEquals(id2, newsletters.findByID(n2.getNewsletterId())); 
	}*/
<<<<<<< HEAD
	
	public void testFindNewsletterByDate() {
		
	}
=======
>>>>>>> branch 'master' of https://github.com/dreid3/York-Engineering-Society-Website-2.0.git
	
	@Test
	public void testChangeTempalte() {
		String template1 = "welcome back";
		String template2 = "Valentines";
		
		newsletters.changeNewsletterTemplate(n, template1);
		newsletters.changeNewsletterTemplate(n2, template2);
		
		assertEquals("welcome back", n.getTemplate()); 
		assertEquals("Valentinees", n2.getTemplate()); 
	}
}

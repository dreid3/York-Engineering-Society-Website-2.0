package modelTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test; 

import yorkEngineeringSociety.models.Newsletter;

public class NewsletterTest {
	
	private Newsletter newsletter; 
	private Newsletter newsletter2;
	
	@Before
	public void setUp() {
		newsletter = new Newsletter(); 
		newsletter2 = new Newsletter(); 
		
		newsletter.setName("Welcome");
		newsletter.setTemplate("");
		newsletter.setNewsletterId(1);
		
		
		newsletter2.setName("Membership Updates");
		newsletter2.setTemplate("");
		newsletter2.setNewsletterId(122);
		
		
	}
	
	@Test
	public void testGetName() {
		String name1 = newsletter.getName();
		String name2 = newsletter2.getName(); 
		
		assertEquals("Welcome", name1); 
		assertEquals("Membership Updates", name2); 
	}
	
	
	public void testGetMonth() {
		//String month1 = newsletter.getMonth(); 
		//String month2 = newsletter2.getMonth();
		
		//assertEquals("November", month1); 
		//assertEquals("March", month2); 
	}
	
	@Test
	public void testGetTemplate() {
		String Template1 = newsletter.getTemplate(); 
		String Tempalte2 = newsletter.getTemplate();
		
		assertEquals("", Template1); 
		assertEquals("", Tempalte2); 
	}
	
	@Test
	public void testGetID() {
		long ID1 = newsletter.getNewsletterId();
		long ID2 = newsletter2.getNewsletterId();
		
		assertEquals(ID1, 1); 
		assertEquals(ID2, 122); 
	}
	
	
}

package modelTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test; 

import yorkEngineeringSociety.models.Newsletter;

public class NewsletterTest {
	
	private Newsletter newsletter; 
	private Newsletter newsletter2;
	private Newsletter newsletter3; 
	private Newsletter newsletter4;
	private Newsletter newsletter5;
	private List<Newsletter> newsletterList; 
	@Before
	public void setUp() {
		newsletter = new Newsletter(); 
		newsletter2 = new Newsletter(); 
		newsletter3 = new Newsletter(); 
		newsletter4 = new Newsletter(); 
		newsletter5 = new Newsletter(); 
		
		newsletterList = new ArrayList<Newsletter>(); 
				
		newsletter.setName("Welcome");
		newsletter.setTemplate("");
		newsletter.setNewsletterId(1);
		
		
		newsletter2.setName("Membership Updates");
		newsletter2.setTemplate("");
		newsletter2.setNewsletterId(122);
		
		
		newsletter3.setName("asdfer/");
		newsletter4.setName("asdf>");
		newsletter5.setName("alkjsjd}");
		
		newsletterList.add(newsletter3);
		newsletterList.add(newsletter4);
		newsletterList.add(newsletter5); 
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
	
	@Test
	public void invalidNewsletterCharacters() {
		boolean validName = true; 
		
		//itterate through the list of invalid names we created
		for(Newsletter n: newsletterList) {
			String name = n.getName();
			for(int i = 0; i < name.length(); i++) {
				if(name.charAt(i) == '[' || name.charAt(i) == ']'
								|| name.charAt(i) == '(' || name.charAt(i) == ')' 
								|| name.charAt(i) == '}' || name.charAt(i) == '{' 
								|| name.charAt(i) == '/' || name.charAt(i) == '.'
								|| name.charAt(i) == ',' || name.charAt(i) == '<'
								|| name.charAt(i) == '>' ) {
					System.out.println("There was an invalid character in the news letter name");
					validName = false; 
				}
			}
			
		}
		assertFalse(validName);
	}
}

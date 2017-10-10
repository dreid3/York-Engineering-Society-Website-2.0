package ModelTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test; 

import yorkEngineeringSociety.models.User;

public class UserTests {
	//Test users and admin
	
	private User user1, user2, user3;
	private User Admin1, Admin2;
	
	@Before
	public void setUp() {
		//user 1
		user1 = new User();
		user1.setAccountType("User");
		
		user1.setUsername("Sir Galahad");
		user1.setPassword("the pure");
		
		user1.setUserInformation("Graham", "Chapman", "lumberjacks@canada.com");
		
		//set up user 2
		user2 = new User(); 
		user2.setAccountType("User");
		
		user2.setUsername("Eldest");
		user2.setPassword("gruff");
		
		user2.setUserInformation("Tiny", "Tim", "summereqsuire@e.org");
		
		user3 = new User(); 
		user3.setAccountType("");
		user3.setUsername("");
		user3.setPassword("");
		user3.setUserInformation("", "", "");
		
		Admin1 = new User();
		
		Admin1.setAccountType("Admin");
		
		Admin1.setUsername("Summer Queen");
		Admin1.setPassword("summer4eva");
		Admin1.setUserInformation("Titania", "", "lightMonarch1@s.net");
	
		
	}
	
	@Test
	public void testNames() {
		String username1 = user1.getUsername();
		String adminName1 = Admin1.getUsername();
		
		assertEquals("Sir Galahad", username1);
		assertEquals("Summer Queen", adminName1);
	}
	
	@Test
	public void testPasswords() {
		String pass1 = user1.getPassword();
		String pass2 = user2.getPassword();
		String pass3 = Admin1.getPassword();
		
		assertEquals("the pure", pass1);
		assertEquals("gruff", pass2);
		assertEquals("summer4eva", pass3);
	}
	
	@Test 
	public void testPersonalInfo() {
		String fname = user2.getFirstname();
		String lname = user2.getLastname();
		String email = user2.getEmail();
		
		assertEquals("Tiny", fname);
		assertEquals("Tim", lname);
		assertEquals("summereqsuire@e.org", email);
	}
	
	@Test 
	public void testAdminRights() {
		String user = user1.getAccountType();
		String admin = Admin1.getAccountType();
		
		assertEquals("User", user);
		assertEquals("Admin", admin);
	}
	
	@Test
	public void testNull() {
		String password = user3.getPassword();
		String username = user3.getUsername();
		String type = user3.getAccountType();
		
		assertNull(password);
		assertNull(username);
		assertNull(type);
		
	}
}

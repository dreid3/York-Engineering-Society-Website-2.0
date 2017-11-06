package serviceTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test; 

import yorkEngineeringSociety.models.User; 
import yorkEngineeringSociety.services.UserServerImpl;
public class UserServiceTest {
	UserServerImpl users; 
	User testUser1;
	User testUser2;
	User nullUser;
	@Before
	public void setUp() {
		users = new UserServerImpl(); 
		
	    testUser1 = new User(); 
		testUser2 = new User(); 
		nullUser  = new User(); 
		
		testUser1.setFirstname("Ryan");
		testUser1.setEmail("rvincent@ycp.edu");
		testUser1.setActive(true);
		testUser1.setAsMember();
		
		testUser2.setFirstname("Josh");
		testUser2.setEmail("jMem@gmail.com");
		testUser2.setActive(false);
		testUser2.setAsMember();
		
		nullUser.setFirstname("");
		nullUser.setEmail("");
		nullUser.setActive(false);
		nullUser.setMembership(false);
	}
	
	@Test
	public void testSearchByFirstname() {
		assertEquals("Ryan", users.findByFirstname(testUser1.getFirstname()));
		assertEquals("Josh", users.findByFirstname(testUser2.getFirstname())); 
		assertEquals("", users.findByFirstname(nullUser.getFirstname()));
	}
	
	@Test
	public void testSearchByEmail() {
		assertEquals("rvincent@ycp.edu", users.findUserByEmail(testUser1.getEmail()));
		assertEquals("jMem@gmail.com", users.findUserByEmail(testUser2.getEmail()));
		assertEquals("", users.findByFirstname(nullUser.getFirstname()));
	}
	
	public void testNull() {

	}
	
	@Test
	public void testUpdateMemberShip() {
		//set initial values so that the service impl can change them
		boolean active = true;
		boolean inactive = false; 
		
		assertEquals(active, testUser1.isMembership()); 
		assertEquals(inactive, testUser2.isMembership()); 
		
		//alternate their memberships 
		users.updateUserMembership(testUser1);
		users.updateUserMembership(testUser2); 
		
		assertFalse(testUser1.isMembership()); 
		assertTrue(testUser1.isMembership());
	}
	
	@Test
	public void testUpdateActive() {
		
		assertTrue(testUser1.isActive()); 
		
		//set user as inactive
		users.inactivateUser(testUser1);
		
		assertFalse(testUser1.isActive());
	}
	
}

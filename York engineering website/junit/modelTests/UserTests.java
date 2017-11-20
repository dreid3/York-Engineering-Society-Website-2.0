package modelTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test; 

import yorkEngineeringSociety.models.User;

public class UserTests {
	//Test users and admin

	private User user1, user2, user3, user4, user5;
	private User Admin1, admin2, Admin3;
	private List<User> Gooduserlist; 
	private List<User> badUserlist;

	private List<User> adminlist; 
	private List<User> badAdminList; 

	@Before
	public void setUp() {

		Gooduserlist = new ArrayList<User>();
		adminlist = new ArrayList<User>(); 
		badUserlist = new ArrayList<User>();
		badAdminList = new ArrayList<User>(); 

		//user 1
		user1 = new User();
		user1.setAdmin(false);

		user1.setPassword("the pure");

		user1.setUserInformation("Graham", "Chapman", "lumberjacks@canada.com");
		user1.setActive(true);
		user1.setAsMember();

		//set up user 2
		user2 = new User(); 
		user2.setAdmin(false);

		user2.setPassword("gruff");
		user2.setActive(true);
		user2.setUserInformation("Tiny", "Tim", "summereqsuire@e.org");
		user2.setAsMember();

		//boolean f;

		user3 = new User(); 
		//user3.setAdmin(f);

		user3.setPassword("");
		user3.setUserInformation("", "", "");
		user3.setActive(true);
		user3.setAsMember();

		//invalid characters: spaces
		user4 = new User();
		user4.setAdmin(false);
		user4.setActive(true); 
		user4.setAsMember();
		user4.setEmail("IAmAFool @YTEXe.edy");
		user4.setPassword("Passwor d");

		user5 = new User(); 
		user5.setActive(true);
		user5.setAsMember();
		user5.setEmail("asfe@gm{il.com");
		user5.setPassword("aaer}");

		Admin1 = new User();

		Admin1.setAdmin(true);


		Admin1.setPassword("summer4eva");
		Admin1.setUserInformation("Titania", "", "lightMonarch1@s.net");
		Admin1.setActive(true);
		Admin1.setAsMember();

		//invalid characters: parenthesis
		admin2 = new User(); 
		admin2.setAdmin(true);
		admin2.setActive(true);
		admin2.setAsMember();
		admin2.setEmail("whyAreTHe)sTestsSoAnnoying@mhet.com");
		admin2.setPassword("mee(p");

		//invalid Characters: brackets
		Admin3 = new User(); 
		Admin3.setAdmin(true); 
		Admin3.setActive(true);
		Admin3.setAsMember(); 
		Admin3.setEmail("[arer@google.com");
		Admin3.setPassword("ahemr]er");

		//populate lists to test searching and matching methods
		Gooduserlist.add(user1);
		Gooduserlist.add(user2);
		badUserlist.add(user3);
		badUserlist.add(user4);
		badUserlist.add(user5);

		adminlist.add(Admin1);
		badAdminList.add(admin2);
		badAdminList.add(Admin3); 

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
		boolean user = user1.isAdmin();
		boolean admin = Admin1.isAdmin();

		assertEquals(false, user);
		assertEquals(true, admin);
	}

	@Test
	public void testNull() {
		String password = user3.getPassword();
		String username = user3.getEmail();
		//String type = user3.getAccountType();

		assertEquals(password, "");
		assertEquals(username, "");
		//assertNull(type);

	}

	@Test
	public void testVoidactive() {
		String username = user3.getEmail();
		if(username == null || username == "") {
			user3.setActive(false);
		}

		assertFalse(user3.isActive());

	}

	@Test
	public void testMembership() {
		assertTrue(user1.isMembership());
		assertTrue(user2.isMembership()); 
		assertTrue(user3.isMembership());

		assertTrue(Admin1.isMembership());
	}

	@Test
	public void testInvalidNameCharacters() {
		
		boolean validname = true; 

		//sift through the user list, should generate no failures  
		for(User user: Gooduserlist) {
			if(!user.isMembership()) {
				System.out.println("User is not a member, and cannot be tested");
				break; 
			}
			else {
				if(!user.isActive()) {
					System.out.println("User does not have an active account, end the test of them");
				}
				else {
					//test for invalidity in here 
					String email  = user.getEmail();
					for(int i = 0; i < email.length(); i++) {
						if(email.charAt(i) == ' ' || email.charAt(i) == '[' || email.charAt(i) == ']'
								|| email.charAt(i) == '(' || email.charAt(i) == ')' 
								|| email.charAt(i) == '}' || email.charAt(i) == '{'
								|| email.charAt(i) == '/' || email.charAt(i) == ',' 
								|| email.charAt(i) == '<'|| email.charAt(i) == '>') {
							System.out.println("There was an invalid character in your username");
							validname = false; 
						}
						else {
							validname = true;
						}
					} 
				}

			} 
			
		}
		//make assertion up to this point 
		assertTrue(validname);
		if(!validname) {validname = true;}
		
		//sift through the bad user list, this should generate failures 
		for(User user: badUserlist) {
			if(!user.isMembership()) {
				System.out.println("User is not a member, and cannot be tested");
				break; 
			}
			else {
				if(!user.isActive()) {
					System.out.println("User does not have an active account, end the test of them");
				}
				else {
					//test for invalidity in here 
					String email = user.getEmail();
					for(int i = 0; i < email.length(); i++) {
						if(email.charAt(i) == ' ' || email.charAt(i) == '[' || email.charAt(i) == ']'
								|| email.charAt(i) == '(' || email.charAt(i) == ')' 
								|| email.charAt(i) == '}' || email.charAt(i) == '{'
								|| email.charAt(i) == '/' || email.charAt(i) == ',' 
								|| email.charAt(i) == '<' || email.charAt(i) == '>') {
							System.out.println("There was an invalid character in your username");
							validname = false;  
						}
						else {
							//validname = true; 
						}
					}
				}
			}
		}
		
			
		assertFalse(validname); 
		if(!validname) {validname = true;}

		//sift through the good admin list 
		for(User user: adminlist) {
			if(!user.isMembership()) {
				System.out.println("User is not a member, and cannot be tested");
				break; 
			}
			else {
				if(!user.isActive()) {
					System.out.println("User does not have an active account, end the test of them");
				}
				else {
					//test for invalidity in here 
					String email = user.getEmail();
					for(int i = 0; i < email.length(); i++) {
						if(email.charAt(i) == ' ' || email.charAt(i) == '[' || email.charAt(i) == ']'
								|| email.charAt(i) == '(' || email.charAt(i) == ')' 
								|| email.charAt(i) == '}' || email.charAt(i) == '{'
								|| email.charAt(i) == '/' || email.charAt(i) == ',' 
								|| email.charAt(i) == '<' || email.charAt(i) == '>') {
							System.out.println("There was an invalid character in your password");
							validname = false;  
						}
						else {
							validname = true; 
						}
					}
				}
			}
			
		}
		
		
		assertTrue(validname); 
		if(!validname) {validname = true;}
		
		//sift through the bad admin list, should result in errors 
		for(User user: badAdminList) {
			if(!user.isMembership()) {
				System.out.println("User is not a member, and cannot be tested");
				break; 
			}
			else {
				if(!user.isActive()) {
					System.out.println("User does not have an active account, end the test of them");
				}
				else {
					//test for invalidity in here 
					String email = user.getEmail();
					for(int i = 0; i < email.length(); i++) {
						if(email.charAt(i) == ' ' || email.charAt(i) == '[' || email.charAt(i) == ']'
								|| email.charAt(i) == '(' || email.charAt(i) == ')' 
								|| email.charAt(i) == '}' || email.charAt(i) == '{' 
								|| email.charAt(i) == '/' || email.charAt(i) == ',' 
								|| email.charAt(i) == '<'|| email.charAt(i) == '>' )  {

							System.out.println("There was an invalid character in your username");
							validname = false;  
						}
						else {
							//validname = true; 
						}
					}
				}
				
			}
		} 
		assertFalse(validname); 
	}

	@Test
	public void testInvalidPasswordCharacters() {

		boolean validPassword = true; 

		//sift through the good user list 
		for(User user: Gooduserlist) {
			if(!user.isMembership()) {
				System.out.println("User is not a member, and cannot be tested");
				break; 
			}
			else {
				if(!user.isActive()) {
					System.out.println("User does not have an active account, end the test of them");
				}
				else {
					//test for invalidity in here 
					String password = user.getPassword();
					for(int i = 0; i < password.length(); i++) {
						if(password.charAt(i) == ' ' || password.charAt(i) == '[' || password.charAt(i) == ']'
								|| password.charAt(i) == '(' || password.charAt(i) == ')' 
								|| password.charAt(i) == '}' || password.charAt(i) == '{' 
								|| password.charAt(i) == '/' || password.charAt(i) == '.'
								|| password.charAt(i) == ',' || password.charAt(i) == '<'
								|| password.charAt(i) == '>' ) {
							System.out.println("There was an invalid character in your password");
							validPassword = false;  
						}
						else {
							validPassword = true; 
						}
					}
				}
				
			}
		}
		
		if(!validPassword) {validPassword = true;}
		assertTrue(validPassword);
		

		//sift through the admin list 
		for(User user: adminlist) {
			if(!user.isMembership()) {
				System.out.println("User is not a member, and cannot be tested");
				break; 
			}
			else {
				if(!user.isActive()) {
					System.out.println("User does not have an active account, end the test of them");
				}
				else {
					//test for invalidity in here 
					String password = user.getPassword();
					for(int i = 0; i < password.length(); i++) {
						if(password.charAt(i) == ' ' || password.charAt(i) == '[' || password.charAt(i) == ']'
								|| password.charAt(i) == '(' || password.charAt(i) == ')' 
								|| password.charAt(i) == '}' || password.charAt(i) == '{' 
								|| password.charAt(i) == '/' || password.charAt(i) == '.'
								|| password.charAt(i) == ',' || password.charAt(i) == '<'
								|| password.charAt(i) == '>' ) {
							System.out.println("There was an invalid character in your password");
							validPassword = false;  
						}
						else {
							validPassword = true; 
						}
					}
				}
			}
		}
		
		assertTrue(validPassword);
		if(!validPassword) {validPassword = true;}
		
		//sift through the bad user list, should cause a failure 
		for(User user: badUserlist) {
			if(!user.isMembership()) {
				System.out.println("User is not a member, and cannot be tested");
				break; 
			}
			else {
				if(!user.isActive()) {
					System.out.println("User does not have an active account, end the test of them");
				}
				else {
					//test for invalidity in here 
					String password = user.getPassword();
					for(int i = 0; i < password.length(); i++) {
						if(password.charAt(i) == ' ' || password.charAt(i) == '[' || password.charAt(i) == ']'
								|| password.charAt(i) == '(' || password.charAt(i) == ')' 
								|| password.charAt(i) == '}' || password.charAt(i) == '{' 
								|| password.charAt(i) == '/' || password.charAt(i) == '.'
								|| password.charAt(i) == ',' || password.charAt(i) == '<'
								|| password.charAt(i) == '>' ) {
							System.out.println("There was an invalid character in your password");
							validPassword = false;  
						}
						else {
							validPassword = true; 
						}
					}
				}
			}
			
		}
		
		assertFalse(validPassword);
		if(!validPassword) {validPassword = true;}
		
		//sift through the bad admin list, should cause a failure 
		for(User user: badAdminList) {
			if(!user.isMembership()) {
				System.out.println("User is not a member, and cannot be tested");
				break; 
			}
			else {
				if(!user.isActive()) {
					System.out.println("User does not have an active account, end the test of them");
				}
				else {
					//test for invalidity in here 
					String password = user.getPassword();
					for(int i = 0; i < password.length(); i++) {
						if(password.charAt(i) == ' ' || password.charAt(i) == '[' || password.charAt(i) == ']'
								|| password.charAt(i) == '(' || password.charAt(i) == ')' 
								|| password.charAt(i) == '}' || password.charAt(i) == '{' 
								|| password.charAt(i) == '/' || password.charAt(i) == '.'
								|| password.charAt(i) == ',' || password.charAt(i) == '<'
								|| password.charAt(i) == '>' ) {
							System.out.println("There was an invalid character in your password");
							validPassword = false;  
						}
						else {
							//validPassword = true; 
						}
					}
				}
			}
			
		}
		assertFalse(validPassword);
	}


}

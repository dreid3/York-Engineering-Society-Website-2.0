package yorkEngineeringSociety.services;

import yorkEngineeringSociety.models.User;

public interface UserService {
	User findUserByEmail(String arg0);

	void saveUser(User arg0);

	User userLogin(String arg0, String arg1);
	
	User findByFirstname(String arg0);
	
	//automatically set a user as inactive 
	boolean setAsInactive(User user);
	
	//for admins to disable user accounts, archiving them 
	boolean ManuallydisableMembership(User user);
	
}
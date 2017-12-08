package yorkEngineeringSociety.services;

import yorkEngineeringSociety.models.User;

public interface UserService {
	User findUserByEmail(String arg0);

	void saveUser(User arg0);

	User userLogin(String arg0, String arg1);
	
	User findByFirstname(String arg0);
	
	User findAllUser(); 
	
	boolean changePassword(User user, String password, String newPassword);
	
    void inactivateUser(User user);
    
    void updateUserMembership(User user);

	void changePasswordEmail(User user, String password);
}
package yorkEngineeringSociety.models;

public class User {

	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	private boolean membership = false; 
	private int userID; 
	private String accountType; 
	private String sessionID; 
	
	private boolean login = false; 
	
	public User(){
		
	}
	
	//utility personal information method
	public void setUserInformation(String fn, String ln, String email) {
		this.firstname = fn;
		this.lastname = ln;
		this.email = email;
	}
	
	//place holder method for now will have functionality later 
	public boolean authenticate() {
		return false;
	}
	
	//to use in the login servlet 
	public boolean logIn(String username, String password) {
		if(login != true) {
			login = true; 
		}
		
		return login;
	}
	
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	
	
	/*public User(String user, String pass, int id, String fisrtname, String lastname, String email, boolean membership){
		this.username = user;
		this.password = pass;
		this.loginId = id;
		this.firstname = firstname;
		this.setLastname(lastname); 
		this.email = email;
		this.membership = membership;
		
	}*/
	
	public String getUsername(){
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	
	
	public String getName(){
		return this.firstname;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	/*public boolean isMember(){
		return this.membership;
	}*/
	
	
	
	public void setUsername(String user){
		this.username = user;
	}
	
	public void setPassword(String pass){
		this.password = pass;
	}
	
	
	
	public void setFirstname(String firstname){
		this.firstname = firstname;
	}
	
	public void setEmail(String email){
			this.email = email;
	}
	
	public void setAsMember() {
		this.membership = true;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean isMembership() {
		return membership;
	}

	public void setMembership(boolean membership) {
		this.membership = membership;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public String getFirstname() {
		return firstname;
	}

}

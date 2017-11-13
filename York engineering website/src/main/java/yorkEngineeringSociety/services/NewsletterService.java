package yorkEngineeringSociety.services;

import java.util.List;

import yorkEngineeringSociety.models.Newsletter;

public interface NewsletterService {

	Newsletter findByName(String arg0);
	
	Newsletter findByID(long arg0);
	
	//to change a newsletter later 
	void changeNewsletterTemplate(Newsletter n, String template); 
	
	//find all news letters 
	List<Newsletter> getNewslettersOrderedByDate();
	
}

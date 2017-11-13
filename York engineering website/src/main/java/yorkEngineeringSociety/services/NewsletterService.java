package yorkEngineeringSociety.services;

import java.util.List;

import yorkEngineeringSociety.models.Newsletter;

public interface NewsletterService {
	
	//to change a newsletter later 
	void changeNewsletterTemplate(Newsletter n, String template); 
	
	//find all news letters 
	List<Newsletter> getNewslettersOrderedByDate();
	
}

package yorkEngineeringSociety.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yorkEngineeringSociety.models.Newsletter;
import yorkEngineeringSociety.repos.NewsletterRepository;
import yorkEngineeringSociety.services.NewsletterService;

@Service("newsletterService")
public class NewsletterServerImpl implements NewsletterService{
	
	@Autowired
	private NewsletterRepository newsletterRepo; 
	

	@Override
	public void changeNewsletterTemplate(Newsletter n, String template) {
		n.setTemplate(template);
		this.newsletterRepo.save(n);
	}

	@Override
	public List<Newsletter> getNewslettersOrderedByDate() {
		List<Newsletter> newsletters = newsletterRepo.findAll(); 
		
		Collections.sort(newsletters, new Comparator<Newsletter>() {
		public int compare(Newsletter one, Newsletter two) {
			return one.getCalendar().compareTo(two.getCalendar()); 
			}
		}); 
		
		return newsletters;
	}


}

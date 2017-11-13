package yorkEngineeringSociety.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import yorkEngineeringSociety.models.Newsletter;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {
	Newsletter findByName(String arg0);
	Newsletter findByID(int arg0);
	
	//List<Newsletter> getNewslettersOrderedByDate();
}

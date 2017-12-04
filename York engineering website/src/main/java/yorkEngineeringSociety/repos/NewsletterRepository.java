package yorkEngineeringSociety.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import yorkEngineeringSociety.models.Newsletter;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {
	
	//List<Newsletter> getNewslettersOrderedByDate();
	Newsletter findByName(String arg0);
}

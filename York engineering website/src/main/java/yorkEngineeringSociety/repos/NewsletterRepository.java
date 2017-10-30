package yorkEngineeringSociety.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import yorkEngineeringSociety.models.Newsletter;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {
	Newsletter findByName(String arg0);
}

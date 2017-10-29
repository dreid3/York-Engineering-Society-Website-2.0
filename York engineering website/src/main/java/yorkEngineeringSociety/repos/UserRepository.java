package yorkEngineeringSociety.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import yorkEngineeringSociety.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String arg0);
	User findByFirstname(String arg0);
	
	
}
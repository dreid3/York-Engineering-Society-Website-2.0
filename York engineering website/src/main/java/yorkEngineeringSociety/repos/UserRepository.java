package yorkEngineeringSociety.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import yorkEngineeringSociety.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String arg0);
	User findByFirstname(String arg0);
	User findByUuid(String arg0);
	User findByBlacklistid(String arg0);
	public List<User> findByVerifiedTrueAndBlacklistFalse();
}
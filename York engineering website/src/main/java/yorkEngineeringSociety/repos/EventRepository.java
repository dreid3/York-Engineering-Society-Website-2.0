package yorkEngineeringSociety.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import yorkEngineeringSociety.models.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
	@Transactional
	public List<Event> findByReminderFalse();
	
	@Transactional
	public List<Event> findBySubreminderFalse();
}

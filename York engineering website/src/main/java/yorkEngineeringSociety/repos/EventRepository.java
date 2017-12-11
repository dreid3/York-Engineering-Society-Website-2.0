package yorkEngineeringSociety.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import yorkEngineeringSociety.models.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
<<<<<<< HEAD

=======
	@Transactional
	public List<Event> findByReminderFalse();
	
	@Transactional
	public List<Event> findBySubreminderFalse();
>>>>>>> branch 'master' of https://github.com/dreid3/York-Engineering-Society-Website-2.0.git
}

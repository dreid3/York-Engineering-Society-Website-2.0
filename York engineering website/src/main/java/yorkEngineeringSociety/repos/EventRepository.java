package yorkEngineeringSociety.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import yorkEngineeringSociety.models.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
	Event findByName(String name);
}

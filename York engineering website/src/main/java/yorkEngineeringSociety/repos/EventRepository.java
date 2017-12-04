package yorkEngineeringSociety.repos;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import yorkEngineeringSociety.models.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}

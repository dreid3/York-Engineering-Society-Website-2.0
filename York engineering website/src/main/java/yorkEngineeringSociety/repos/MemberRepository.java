package yorkEngineeringSociety.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import yorkEngineeringSociety.models.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {


}

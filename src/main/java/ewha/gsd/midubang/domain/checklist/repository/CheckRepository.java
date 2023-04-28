package ewha.gsd.midubang.domain.checklist.repository;

import ewha.gsd.midubang.domain.checklist.entity.Check;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckRepository extends JpaRepository<Check, Long> {
}

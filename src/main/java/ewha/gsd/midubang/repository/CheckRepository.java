package ewha.gsd.midubang.repository;

import ewha.gsd.midubang.entity.Check;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckRepository extends JpaRepository<Check, Long> {
}

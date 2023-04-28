package ewha.gsd.midubang.domain.community.repository;

import ewha.gsd.midubang.domain.community.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}

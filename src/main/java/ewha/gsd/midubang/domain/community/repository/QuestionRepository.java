package ewha.gsd.midubang.domain.community.repository;

import ewha.gsd.midubang.domain.community.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}

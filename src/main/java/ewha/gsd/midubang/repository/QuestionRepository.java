package ewha.gsd.midubang.repository;

import ewha.gsd.midubang.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}

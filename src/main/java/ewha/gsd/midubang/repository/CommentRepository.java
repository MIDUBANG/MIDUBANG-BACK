package ewha.gsd.midubang.repository;

import ewha.gsd.midubang.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

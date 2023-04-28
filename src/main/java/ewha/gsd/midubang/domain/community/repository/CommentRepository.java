package ewha.gsd.midubang.domain.community.repository;

import ewha.gsd.midubang.domain.community.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

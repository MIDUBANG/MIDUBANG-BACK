package ewha.gsd.midubang.domain.community.repository;

import ewha.gsd.midubang.domain.community.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

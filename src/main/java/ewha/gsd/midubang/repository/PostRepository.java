package ewha.gsd.midubang.repository;

import ewha.gsd.midubang.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

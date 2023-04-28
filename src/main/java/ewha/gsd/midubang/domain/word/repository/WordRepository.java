package ewha.gsd.midubang.domain.word.repository;

import ewha.gsd.midubang.domain.word.entity.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Long>, WordRepositoryCustom {
    Page<Word> findByWordContaining(String keyword, Pageable pageable);
    Page<Word> findAll(Pageable pageable);
}

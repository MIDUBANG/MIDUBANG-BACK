package ewha.gsd.midubang.repository;

import ewha.gsd.midubang.entity.Word;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long>,WordRepositoryCustom{
    Page<Word> findByWordContaining(String keyword, Pageable pageable);
    Page<Word> findAll(Pageable pageable);
}

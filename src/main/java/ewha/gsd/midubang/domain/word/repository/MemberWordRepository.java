package ewha.gsd.midubang.domain.word.repository;

import ewha.gsd.midubang.domain.word.entity.MemberWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberWordRepository extends JpaRepository<MemberWord, Long>{

}

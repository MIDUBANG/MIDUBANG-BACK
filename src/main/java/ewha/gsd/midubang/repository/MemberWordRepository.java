package ewha.gsd.midubang.repository;

import ewha.gsd.midubang.entity.MemberWord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public interface MemberWordRepository extends JpaRepository<MemberWord, Long>{

}

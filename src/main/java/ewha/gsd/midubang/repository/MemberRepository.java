package ewha.gsd.midubang.repository;


import ewha.gsd.midubang.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public boolean existsByEmail(String email);
    public Member findByEmail(String email);
}

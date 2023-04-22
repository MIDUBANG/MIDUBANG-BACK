package ewha.gsd.midubang.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static ewha.gsd.midubang.entity.QChecklist.checklist;

@Repository
@RequiredArgsConstructor
public class ChecklistQuerydslRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Integer> findChecklistIdByMemberId(Long memberId) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return jpaQueryFactory
                .select(checklist.checklistId)
                .where(checklist.memberId.eq(memberId))
                .from(checklist)
                .fetch();
    }
}

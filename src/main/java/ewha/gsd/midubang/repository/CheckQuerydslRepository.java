package ewha.gsd.midubang.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ewha.gsd.midubang.dto.ChecklistDto;
import ewha.gsd.midubang.entity.ChecklistContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static ewha.gsd.midubang.entity.QCheck.check;
import static ewha.gsd.midubang.entity.QChecklistContent.checklistContent;

@Repository
@RequiredArgsConstructor
public class CheckQuerydslRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Integer> findChecklistIdByMemberId(Long memberId) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return jpaQueryFactory
                .select(check.checklistContent.checklistId)
                .where(check.memberId.eq(memberId))
                .from(check)
                .fetch();
    }

    @Transactional(readOnly = true)
    public List<ChecklistDto> findChecklistByCategoryId(Integer categoryId) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return jpaQueryFactory
                .select(Projections.fields(ChecklistDto.class,
                        checklistContent.checklistId,
                        checklistContent.listName,
                        checklistContent.listDetail
                        ))
                .where(checklistContent.categoryId.categoryId.eq(categoryId))
                .from(checklistContent)
                .fetch();
    }

    @Transactional(readOnly = true)
    public boolean existCheckByMemberIdAndChecklistId(Long memberId, Integer checklistId) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        Integer fetchFirst = jpaQueryFactory
                .selectOne()
                .from(check)
                .where(check.memberId.eq(memberId)
                        .and(check.checklistContent.checklistId.eq(checklistId)))
                .fetchFirst();

        return fetchFirst != null;
    }

    @Transactional
    public long deleteCheckByMemberIdAndChecklistId(Long memberId, Integer checklistId) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return jpaQueryFactory
                .delete(check)
                .where(check.memberId.eq(memberId)
                        .and(check.checklistContent.checklistId.eq(checklistId)))
                .execute();
    }
}

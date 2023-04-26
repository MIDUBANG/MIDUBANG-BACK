package ewha.gsd.midubang.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ewha.gsd.midubang.entity.ChecklistContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static ewha.gsd.midubang.entity.QChecklistContent.checklistContent;

@Repository
@RequiredArgsConstructor
public class ChecklistContentQuerydslRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public Integer getCountByCategoryId(Integer categoryId) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return Math.toIntExact(jpaQueryFactory
                .select(checklistContent.count())
                .where(checklistContent.categoryId.categoryId.eq(categoryId))
                .from(checklistContent)
                .fetchFirst());
    }

    @Transactional(readOnly = true)
    public List<ChecklistContent> findAllChecklistContentByCategoryId(Integer categoryId) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return jpaQueryFactory
                .selectFrom(checklistContent)
                .where(checklistContent.categoryId.categoryId.eq(categoryId))
                .fetch();
    }

}

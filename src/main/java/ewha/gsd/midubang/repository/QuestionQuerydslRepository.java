package ewha.gsd.midubang.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static ewha.gsd.midubang.entity.QQuestion.question;

@Repository
@RequiredArgsConstructor
public class QuestionQuerydslRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<String> findAllTrueQuestions() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return jpaQueryFactory
                .select(question.content)
                .where(question.questionType.eq(Boolean.TRUE))
                .from(question)
                .orderBy(question.questionId.desc())
                .limit(10)
                .fetch();
    }

    @Transactional(readOnly = true)
    public List<String> findAllFalseQuestions() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return jpaQueryFactory
                .select(question.content)
                .where(question.questionType.eq(Boolean.FALSE))
                .from(question)
                .orderBy(question.questionId.desc())
                .limit(10)
                .fetch();
    }
}

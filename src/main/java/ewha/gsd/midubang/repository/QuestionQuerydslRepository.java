package ewha.gsd.midubang.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ewha.gsd.midubang.dto.CommentListDto;
import ewha.gsd.midubang.dto.PostDetailDto;
import ewha.gsd.midubang.dto.QuestionDetailDto;
import ewha.gsd.midubang.dto.QuestionListDto;
import ewha.gsd.midubang.entity.Post;
import ewha.gsd.midubang.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

import static ewha.gsd.midubang.entity.QQuestion.question;
import static ewha.gsd.midubang.entity.QAnswer.answer;

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

    @Transactional(readOnly = true)
    public List<QuestionListDto> findAllQuestions() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return jpaQueryFactory
                .select(Projections.fields(QuestionListDto.class,
                        question.questionId,
                        question.content.as("question"),
                        question.answers.size().as("numOfAnswers")))
                .from(question)
                .orderBy(question.questionId.desc())
                .fetch();
    }

    @Transactional(readOnly = true)
    public QuestionDetailDto findQuestionDetailById(Long questionId) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        Question tmp = jpaQueryFactory
                .selectFrom(question)
                .where(question.questionId.eq(questionId))
                .leftJoin(question.answers, answer).fetchJoin()
                .distinct()
                .fetchFirst();

        QuestionDetailDto questionDetailDto = new QuestionDetailDto(
                tmp.getQuestionId(),
                tmp.getContent(),
                tmp.getCreatedDate(),

                tmp.getAnswers().stream()
                        .map(p -> new CommentListDto(
                                p.getId(),
                                p.getMember().getEmail(),
                                p.getContent(),
                                p.getCreatedDate()
                        ))
                        .collect(Collectors.toList())
        );

        return questionDetailDto;
    }

    @Transactional(readOnly = true)
    public List<QuestionListDto> findTodayQuestions(String today) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return jpaQueryFactory
                .select(Projections.fields(QuestionListDto.class,
                        question.questionId,
                        question.content.as("question"),
                        question.answers.size().as("numOfAnswers")))
                .where(question.createdDate.eq(today))
                .from(question)
                .fetch();
    }

}

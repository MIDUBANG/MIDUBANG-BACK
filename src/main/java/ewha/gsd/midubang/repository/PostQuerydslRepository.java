package ewha.gsd.midubang.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ewha.gsd.midubang.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static ewha.gsd.midubang.entity.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostQuerydslRepository {

    @PersistenceContext
    private EntityManager em;


    @Transactional(readOnly = true)
    public Post findByPostId(Long postId) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return jpaQueryFactory
                .selectFrom(post)
                .where(post.postId.eq(postId))
                .fetchFirst();
    }
}

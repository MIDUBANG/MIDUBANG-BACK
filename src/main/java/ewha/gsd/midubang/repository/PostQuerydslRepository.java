package ewha.gsd.midubang.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ewha.gsd.midubang.dto.CommentListDto;
import ewha.gsd.midubang.dto.PostDetailDto;
import ewha.gsd.midubang.dto.PostListDto;
import ewha.gsd.midubang.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.Collectors;

import static ewha.gsd.midubang.entity.QPost.post;
import static ewha.gsd.midubang.entity.QComment.comment;

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

    @Transactional(readOnly = true)
    public List<PostListDto> findAllPosts() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return jpaQueryFactory
                .select(Projections.fields(PostListDto.class,
                        post.writer.email.as("writer"),
                        post.question.as("title"),
                        post.comments.size().as("numOfComments")))
                .from(post)
                .fetch();

    }

    @Transactional(readOnly = true)
    public PostDetailDto findPostDetailByPostId(Long postId) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        Post tmp = jpaQueryFactory
                .selectFrom(post)
                .where(post.postId.eq(postId))
                .leftJoin(post.comments, comment).fetchJoin()
                .distinct()
                .fetchFirst();

        PostDetailDto postDetailDto = new PostDetailDto(
                tmp.getWriter().getEmail(),
                tmp.getQuestion(),
                tmp.getDetail(),
                tmp.getCreatedDate(),

                tmp.getComments().stream()
                        .map(p -> new CommentListDto(
                                p.getMember().getEmail(),
                                p.getContent(),
                                p.getCreatedDate()))
                        .collect(Collectors.toList())

        );

        return postDetailDto;

    }
}

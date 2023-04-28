package ewha.gsd.midubang.domain.community.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ewha.gsd.midubang.domain.community.entity.Post;
import ewha.gsd.midubang.domain.community.dto.CommentListDto;
import ewha.gsd.midubang.domain.community.dto.PostDetailDto;
import ewha.gsd.midubang.domain.community.dto.PostListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.Collectors;

import static ewha.gsd.midubang.domain.community.entity.QPost.post;
import static ewha.gsd.midubang.domain.community.entity.QComment.comment;

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
                        post.postId,
                        post.writer.email.as("writer"),
                        post.question.as("title"),
                        post.detail.as("content"),
                        post.comments.size().as("numOfComments")))
                .from(post)
                .orderBy(post.postId.desc())
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
                tmp.getPostId(),
                tmp.getWriter().getEmail(),
                tmp.getQuestion(),
                tmp.getDetail(),
                tmp.getCreatedDate(),

                tmp.getComments().stream()
                        .map(p -> new CommentListDto(
                                p.getId(),
                                p.getMember().getEmail(),
                                p.getContent(),
                                p.getCreatedDate()))
                        .collect(Collectors.toList())

        );

        return postDetailDto;
    }

    @Transactional(readOnly = true)
    public List<PostListDto> findTodayPosts(String today) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return jpaQueryFactory
                .select(Projections.fields(PostListDto.class,
                        post.postId,
                        post.writer.email.as("writer"),
                        post.question.as("title"),
                        post.detail.as("content"),
                        post.comments.size().as("numOfComments")))
                .where(post.createdDate.contains(today))
                .from(post)
                .orderBy(post.postId.desc())
                .fetch();
    }

}

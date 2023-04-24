package ewha.gsd.midubang.service;

import ewha.gsd.midubang.dto.request.CommentRequestDto;
import ewha.gsd.midubang.dto.request.PostRequestDto;
import ewha.gsd.midubang.entity.Comment;
import ewha.gsd.midubang.entity.Member;
import ewha.gsd.midubang.entity.Post;
import ewha.gsd.midubang.repository.CommentRepository;
import ewha.gsd.midubang.repository.MemberRepository;
import ewha.gsd.midubang.repository.PostQuerydslRepository;
import ewha.gsd.midubang.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommunityService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostQuerydslRepository postQuerydslRepository;
    private final CommentRepository commentRepository;

    /* 금쪽이 글 작성 */
    public Long createPost(Long memberId, PostRequestDto request) {
        Post post = new Post();
        Member member = memberRepository.findByMemberId(memberId);
        // member null 예외처리

        post.setWriter(member);
        post.setQuestion(request.getQuestion());
        post.setDetail(request.getDetail());
        post.setCreatedDate(getCurrentDateTime());
        post.setComments(null);
        Post savedPost = postRepository.save(post);
        return savedPost.getPostId();
    }

    /* 금쪽이 글 삭제 */
    public Boolean deletePost(Long memberId, Long postId) {
        // 작성자 확인
        Post post = postQuerydslRepository.findByPostId(postId);
        // post null 예외처리

        if (post.getWriter().getMemberId() != memberId) {
            return false;
        }
        // 글 삭제
        postRepository.delete(post);
        return true;
    }

    /* 금쪽이 글 상세 조회 */


    /* 금쪽이 글 목록 조회 */


    /* 금쪽이 댓글 작성 */
    public Long createComment(Long postId, Long memberId, CommentRequestDto request) {
        Comment comment = new Comment();
        Member member = memberRepository.findByMemberId(memberId);
        Post post = postQuerydslRepository.findByPostId(postId);
        // member, post null 예외처리

        comment.setMember(member);
        comment.setPost(post);
        comment.setContent(request.getComment());
        comment.setCreatedDate(getCurrentDateTime());

        Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }

    /* 금쪽이 댓글 삭제 */
    public Boolean deleteComment(Long memberId, Long commentId) {
        // 작성자 확인
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment.getMember().getMemberId() != memberId) {
            return false;
        }

        // 댓글 삭제
        commentRepository.delete(comment);
        return true;
    }


    /* 챗쪽이 글 상세 조회 */


    /* 챗쪽이 글 목록 조회 */


    /* 챗쪽이 댓글 작성 */


    /* 챗쪽이 댓글 삭제 */

    private String getCurrentDateTime() {
        Instant instant = Instant.now();
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        ZonedDateTime zdtSeoul = instant.atZone(zoneId);
        String time = zdtSeoul.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return time;
    }
}

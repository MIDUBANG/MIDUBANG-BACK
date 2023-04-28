package ewha.gsd.midubang.domain.community.controller;

import ewha.gsd.midubang.domain.community.service.CommunityService;
import ewha.gsd.midubang.domain.community.dto.*;
import ewha.gsd.midubang.global.dto.Message;
import ewha.gsd.midubang.domain.community.dto.PostDetailDto;
import ewha.gsd.midubang.domain.community.dto.QuestionDetailDto;
import ewha.gsd.midubang.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
@Slf4j
public class CommunityController {

    private final TokenProvider tokenProvider;
    private final CommunityService communityService;

    /* 금쪽이 글 작성 */
    @PostMapping("/post")
    public ResponseEntity createPost(HttpServletRequest request, @RequestBody PostRequestDto requestDto) {
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMemberId();
        return ResponseEntity.ok(
                new IdDto(
                        HttpStatus.OK,
                        communityService.createPost(memberId, requestDto)
                )
        );
    }

    /* 금쪽이 글 삭제 */
    @DeleteMapping("/post/{postId}")
    public ResponseEntity deletePost(HttpServletRequest request, @PathVariable Long postId) {
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMemberId();
        DeleteResponseDto responseDto = communityService.deletePost(memberId, postId);
        return ResponseEntity.ok(
                new Message(
                        responseDto.getStatus(),
                        responseDto.getMessage()
                )
        );
    }

    /* 금쪽이 글 상세 조회 */
    @GetMapping("/post/{postId}")
    public ResponseEntity getPostDetail(@PathVariable Long postId) {

        PostDetailDto body = communityService.getPostDetail(postId);

        if (body == null) {
            return ResponseEntity.ok(
                    new Message(
                            HttpStatus.NOT_FOUND,
                            "The post does not exist."
                    )
            );
        }

        return ResponseEntity.ok(
                new CommunityResponseDto(
                        HttpStatus.OK,
                        body
                )
        );
    }

    /* 금쪽이 글 목록 조회 */
    @GetMapping("/post/all")
    public ResponseEntity getAllPosts() {
        return ResponseEntity.ok(
                new CommunityResponseDto(
                        HttpStatus.OK,
                        communityService.getAllPosts()
                )
        );
    }

    /* 금쪽이 댓글 작성 */
    @PostMapping("/post/{postId}/comment")
    public ResponseEntity createComment(HttpServletRequest request,
                                        @PathVariable Long postId, @RequestBody CommentRequestDto requestDto) {
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMemberId();
        return ResponseEntity.ok(
                new IdDto(
                        HttpStatus.OK,
                        communityService.createComment(postId, memberId, requestDto)
                )
        );
    }

    /* 금쪽이 댓글 삭제 */
    @DeleteMapping("/post/comment/{commentId}")
    public ResponseEntity deleteComment(HttpServletRequest request, @PathVariable Long commentId) {
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMemberId();
        DeleteResponseDto responseDto = communityService.deleteComment(memberId, commentId);
        return ResponseEntity.ok(
                new Message(
                        responseDto.getStatus(),
                        responseDto.getMessage()
                )
        );
    }


    /* chatgpt test */
//    @PostMapping("/test")
//    public List<Question> sendQuestion() {
//        return communityService.getDailyQuestions();
//    }


    /* 챗쪽이 글 상세 조회 */
    @GetMapping("/question/{questionId}")
    public ResponseEntity getQuestionDetail(@PathVariable Long questionId) {
        QuestionDetailDto body = communityService.getQuestionDetail(questionId);

        if (body == null) {
            return ResponseEntity.ok(
                    new Message(
                            HttpStatus.NOT_FOUND,
                            "The question does not exist."
                    )
            );
        }

        return ResponseEntity.ok(
                new CommunityResponseDto(
                        HttpStatus.OK,
                        body
                )
        );
    }


    /* 챗쪽이 글 목록 조회 */
    @GetMapping("/question/all")
    public ResponseEntity getAllQuestions() {
        return ResponseEntity.ok(
                new CommunityResponseDto(
                        HttpStatus.OK,
                        communityService.getAllQuestions()
                )
        );
    }


    /* 챗쪽이 댓글 작성 */
    @PostMapping("/question/{questionId}/answer")
    public ResponseEntity createAnwer(HttpServletRequest request,
                                      @PathVariable Long questionId, @RequestBody CommentRequestDto requestDto) {
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMemberId();
        return ResponseEntity.ok(
                new IdDto(
                        HttpStatus.OK,
                        communityService.createAnswer(questionId, memberId, requestDto)
                )
        );
    }


    /* 챗쪽이 댓글 삭제 */
    @DeleteMapping("/question/answer/{answerId}")
    public ResponseEntity deleteAnswer(HttpServletRequest request, @PathVariable Long answerId) {
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMemberId();
        DeleteResponseDto responseDto = communityService.deleteAnswer(memberId, answerId);
        return ResponseEntity.ok(
                new Message(
                        responseDto.getStatus(),
                        responseDto.getMessage()
                )
        );
    }


    /* 오늘의 질문 */
    @GetMapping("/today")
    public ResponseEntity getTodayQuestions() {
        return ResponseEntity.ok(
                new CommunityResponseDto(
                        HttpStatus.OK,
                        communityService.getTodayQuestions()
                )
        );
    }

}

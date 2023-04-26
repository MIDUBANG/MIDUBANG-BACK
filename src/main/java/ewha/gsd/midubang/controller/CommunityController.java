package ewha.gsd.midubang.controller;

import ewha.gsd.midubang.dto.Message;
import ewha.gsd.midubang.dto.PostDetailDto;
import ewha.gsd.midubang.dto.request.CommentRequestDto;
import ewha.gsd.midubang.dto.request.MessageRequestDto;
import ewha.gsd.midubang.dto.request.PostRequestDto;
import ewha.gsd.midubang.dto.IdDto;
import ewha.gsd.midubang.dto.response.ChatGptResponseDto;
import ewha.gsd.midubang.entity.Question;
import ewha.gsd.midubang.jwt.TokenProvider;
import ewha.gsd.midubang.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        if (!communityService.deletePost(memberId, postId)) {
            return ResponseEntity.ok(
                    new Message(
                            HttpStatus.FORBIDDEN,
                            "작성자만 삭제할 수 있습니다."
                    )
            );
        }
        return ResponseEntity.ok(
                new Message(
                        HttpStatus.OK,
                        "삭제 성공"
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
                body
        );
    }

    /* 금쪽이 글 목록 조회 */
    @GetMapping("/post/all")
    public ResponseEntity getAllPostList() {
        return ResponseEntity.ok(
                communityService.getAllPostList()
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
        if (!communityService.deleteComment(memberId, commentId)) {
            return ResponseEntity.ok(
                    new Message(
                            HttpStatus.FORBIDDEN,
                            "작성자만 삭제할 수 있습니다."
                    )
            );
        }
        return ResponseEntity.ok(
                new Message(
                        HttpStatus.OK,
                        "삭제 성공"
                )
        );
    }

    /* chatgpt test */
//    @PostMapping("/test")
//    public List<Question> sendQuestion() {
//        return communityService.getDailyQuestions();
//    }

    /* 챗쪽이 글 상세 조회 */


    /* 챗쪽이 글 목록 조회 */


    /* 챗쪽이 댓글 작성 */


    /* 챗쪽이 댓글 삭제 */

}

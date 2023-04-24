package ewha.gsd.midubang.controller;

import ewha.gsd.midubang.dto.Message;
import ewha.gsd.midubang.dto.request.PostRequestDto;
import ewha.gsd.midubang.dto.response.PostResponseDto;
import ewha.gsd.midubang.jwt.TokenProvider;
import ewha.gsd.midubang.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
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
                new PostResponseDto(
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

    /* 금쪽이 글 목록 조회 */


    /* 금쪽이 댓글 작성 */


    /* 금쪽이 댓글 삭제 */


    /* 챗쪽이 글 목록 조회 */


    /* 챗쪽이 댓글 작성 */


    /* 챗쪽이 댓글 삭제 */

}

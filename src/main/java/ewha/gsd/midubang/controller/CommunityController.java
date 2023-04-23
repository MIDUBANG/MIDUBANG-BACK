package ewha.gsd.midubang.controller;

import ewha.gsd.midubang.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
@Slf4j
public class CommunityController {

    private final TokenProvider tokenProvider;

//    /* 금쪽이 글 작성 */
//    @PostMapping("/human")
//    public ResponseEntity createHumanPost(HttpServletRequest request) {
//        Long memberId = tokenProvider.getUserInfoByRequest(request).getMember_id();
//
//    }

}

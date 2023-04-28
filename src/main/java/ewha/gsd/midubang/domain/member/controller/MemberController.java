package ewha.gsd.midubang.domain.member.controller;


import ewha.gsd.midubang.domain.member.service.KakaoService;
import ewha.gsd.midubang.domain.member.entity.Member;
import ewha.gsd.midubang.domain.member.service.MemberService;
import ewha.gsd.midubang.domain.member.dto.AccountDto;
import ewha.gsd.midubang.global.dto.Message;
import ewha.gsd.midubang.domain.member.dto.UserInfoDto;
import ewha.gsd.midubang.global.exception.BadRequestException;
import ewha.gsd.midubang.global.exception.NotFoundException;
import ewha.gsd.midubang.global.jwt.KakaoToken;
import ewha.gsd.midubang.global.jwt.TokenDTO;
import ewha.gsd.midubang.global.dto.newRefreshTokenResponse;
import ewha.gsd.midubang.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final KakaoService kakaoService;
    private  final TokenProvider tokenProvider;

    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> getMyInfo(HttpServletRequest request){
        UserInfoDto userInfoDto = tokenProvider.getUserInfoByRequest(request);
        return ResponseEntity.status(HttpStatus.OK).body(userInfoDto);
    }

    @PostMapping("/login/oauth/kakao")
    public ResponseEntity<TokenDTO> login(@RequestParam("code") String code) throws IOException, ServletException {
        KakaoToken oauthToken = kakaoService.getAccessToken(code);
        Member saved_member = kakaoService.saveUser(oauthToken.getAccess_token());

        TokenDTO jwtToken = memberService.joinJwtToken(saved_member.getEmail());

        return ResponseEntity.ok(jwtToken);
    }

    //인증코드 받기 테스트용
//    @GetMapping("/login/oauth2/code/kakao")
//    public String KakaoCode(@RequestParam("code") String code) {
//        return "카카오 로그인 인증완료, code: " + code;
//    }

    //access token 재발급
    @GetMapping("/refresh")
    public ResponseEntity<newRefreshTokenResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String refreshToken = tokenProvider.resolveToken(request);
        log.info("resolved token : " + refreshToken);
        TokenDTO tokenDTO = memberService.validRefreshToken(refreshToken, response);
        newRefreshTokenResponse token = new newRefreshTokenResponse(tokenDTO.getAccessToken(), tokenDTO.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    /* 회원가입 */
    @PostMapping("/signup")
    public ResponseEntity<TokenDTO> signup(@RequestBody AccountDto accountDto, HttpServletResponse response) throws ServletException, IOException {
        return ResponseEntity.ok(memberService.signup(accountDto,response));
    }

    /* 로그인 */
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AccountDto accountDto, HttpServletResponse response) throws IOException, ServletException {
        return ResponseEntity.ok(memberService.login(accountDto,response));
    }

    // Error
    @ExceptionHandler
    public ResponseEntity<Message> handleBadRequestException(BadRequestException e) {
        Message message = new Message(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler
    public ResponseEntity<Message> handleNotFoundException(NotFoundException e) {
        Message message = new Message(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

}

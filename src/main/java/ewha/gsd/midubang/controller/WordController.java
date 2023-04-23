package ewha.gsd.midubang.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ewha.gsd.midubang.dto.MemberWordDto;
import ewha.gsd.midubang.dto.UserInfoDto;
import ewha.gsd.midubang.dto.response.*;
import ewha.gsd.midubang.entity.Member;
import ewha.gsd.midubang.entity.MemberWord;
import ewha.gsd.midubang.jwt.TokenProvider;
import ewha.gsd.midubang.service.MemberService;
import ewha.gsd.midubang.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WordController {
    private final TokenProvider tokenProvider;
    private final WordService wordService;
    private final MemberService memberService;




    @PostMapping("/word/{wordId}")
    public ResponseEntity<MemberWordDto> saveWord(HttpServletRequest request, @PathVariable Long wordId){
        UserInfoDto userInfoDto = tokenProvider.getUserInfoByRequest(request);
        MemberWord memberWord = wordService.addWord(userInfoDto.getMemberId(), wordId);

        MemberWordDto memberWordDto = MemberWordDto.builder()
                .email(userInfoDto.getEmail())
                .wordId(memberWord.getWord().getWordId())
                .word(memberWord.getWord().getWord())
                .meaning(memberWord.getWord().getMeaning())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(memberWordDto);
    }

    @DeleteMapping("/word/{wordId}")
    public ResponseEntity<Object> deleteWord(HttpServletRequest request, @PathVariable Long wordId){
        UserInfoDto userInfoDto = tokenProvider.getUserInfoByRequest(request);
        wordService.deleteWord(userInfoDto.getMemberId(),wordId);

        return ResponseEntity.ok().build();
    }
    //전체 단어 리스트 조회
    @GetMapping("/word/list")
    public ResponseEntity<Page<SimpleWordDto>> getWordList(HttpServletRequest request, @PageableDefault Pageable pageable){
        UserInfoDto userInfoDto = tokenProvider.getUserInfoByRequest(request);
        Page<SimpleWordDto> wordList = wordService.getWordList(userInfoDto.getMemberId(),pageable);
        return ResponseEntity.status(HttpStatus.OK).body(wordList);
    }

    //내 단어 리스트 조회
    @GetMapping("word/my/list")
    public ResponseEntity<Page<WordDto>> getMyWordList(HttpServletRequest request, @PageableDefault Pageable pageable ){
        UserInfoDto userInfoDto = tokenProvider.getUserInfoByRequest(request);
        Page<WordDto> myWordList = wordService.getMyWordList(userInfoDto.getMemberId(), pageable);

        return ResponseEntity.status(HttpStatus.OK).body(myWordList);
    }

    //저장된 단어 중 특정 단어 조회
    @GetMapping("/word/my/{wordId}")
    public ResponseEntity<MyWordResDto> getMyWord(HttpServletRequest request, @PathVariable Long wordId) throws JsonProcessingException {
        UserInfoDto userInfoDto = tokenProvider.getUserInfoByRequest(request);
        MyWordResDto wordDto = wordService.getWord(userInfoDto.getMemberId(), wordId);
        return ResponseEntity.status(HttpStatus.OK).body(wordDto);
    }

    @GetMapping("/word/test")
    public NaverWordDto naverapitest(@RequestParam String keyword) throws JsonProcessingException {
        String url = "https://terms.naver.com/entry.naver?docId=300039&cid=43665&categoryId=43665";
        return wordService.getNaverSearch(keyword);
//        return wordService.crawlingDesc(url);
    }

    //특정 단어 조회
    @GetMapping("/word/{wordId}")
    public ResponseEntity<WordResDto> getAWord(HttpServletRequest request, @PathVariable Long wordId) throws JsonProcessingException {
        UserInfoDto userInfoDto = tokenProvider.getUserInfoByRequest(request);
        WordResDto wordResDto = wordService.getAWord(wordId, userInfoDto.getMemberId());
        return ResponseEntity.status(HttpStatus.OK).body(wordResDto);
    }

    //단어 검색
    @GetMapping("/word/search/list")
    public ResponseEntity<Page<SimpleWordDto>> getSearchWordList(@RequestParam String searchKeyword,  @PageableDefault Pageable pageable){
        Page<SimpleWordDto> searchWordDtos = wordService.getSearchWordList(searchKeyword, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(searchWordDtos);
    }

}

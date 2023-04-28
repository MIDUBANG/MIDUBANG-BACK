package ewha.gsd.midubang.domain.word.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import ewha.gsd.midubang.domain.word.entity.MemberWord;
import ewha.gsd.midubang.domain.word.entity.Word;
import ewha.gsd.midubang.domain.word.repository.MemberWordRepository;
import ewha.gsd.midubang.domain.word.repository.WordRepository;
import ewha.gsd.midubang.global.config.NaverAppProperties;
import ewha.gsd.midubang.domain.word.dto.*;
import ewha.gsd.midubang.domain.member.entity.Member;
import ewha.gsd.midubang.global.exception.ApiRequestException;
import ewha.gsd.midubang.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class WordService {
    private final MemberRepository memberRepository;
    private final WordRepository wordRepository;
    private final MemberWordRepository memberWordRepository;
    private static final String NAVER_API_URL = "https://openapi.naver.com/v1/search/encyc.json";
    private final NaverAppProperties naverAppProperties;
    @Transactional
    public MemberWord addWord(Long memberId, Long wordId){
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new ApiRequestException("user not found"));
        if(!wordRepository.exitsInMyDict(memberId, wordId)){
            Word word = wordRepository.findWordById(wordId);
            if(word==null){
                throw new ApiRequestException("존재하지 않는 단어 id");
            }

            LocalDateTime currentDate = LocalDateTime.now();
            MemberWord memberWord = MemberWord.builder()
                    .member(member)
                    .word(word)
                    .word_date(currentDate)
                    .build();

            memberWordRepository.save(memberWord);
            return memberWord;
        }else{
            throw new ApiRequestException("이미 저장된 단어입니다.");
        }

    }

    @Transactional
    public void deleteWord(Long memberId, Long wordId){
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new ApiRequestException("user not found"));
        wordRepository.deleteWord(memberId, wordId);
    }
    @Transactional(readOnly = true)
    public Page<SimpleWordDto> getWordList(Long memberId, Pageable pageable){
        Page<Word> allWords = wordRepository.findAll(pageable);
        Page<SimpleWordDto> wordList = allWords.map(word -> new SimpleWordDto(word, wordRepository.exitsInMyDict(memberId,word.getWordId())));
        return wordList;
    }
    @Transactional(readOnly = true)
    public Page<WordDto> getMyWordList(Long memberId, Pageable pageable){
        Page<MemberWord> allWords  = wordRepository.findAllByMemberId(memberId, pageable);
        Page<WordDto> myWordList = allWords.map(WordDto::new);

        return myWordList;

    }
    @Transactional(readOnly = true)
    public MyWordResDto getWord(Long memberId, Long wordId) throws JsonProcessingException {
        MemberWord memberWord = wordRepository.findMyWord(memberId, wordId);
        WordDto wordDto = WordDto.builder()
                .memberWord(memberWord)
                .build();
        NaverWordDto naverWordDto = getNaverSearch(wordDto.getWord());
        if(memberWord==null){
            throw new ApiRequestException("word not exist");
        }
        return new MyWordResDto(wordDto, naverWordDto);
    }
    @Transactional(readOnly = true)
    public WordResDto getAWord(Long wordId, Long memberId) throws JsonProcessingException {
        Word aWord = wordRepository.findWordById(wordId);
        if(aWord==null){
            throw new ApiRequestException("word not exist");
        }

        NaverWordDto naverWordDto = getNaverSearch(aWord.getWord());
        if(wordRepository.exitsInMyDict(memberId, wordId)){
            return new WordResDto(new SimpleWordDto(aWord,true),naverWordDto );
        }else{
            return new WordResDto(new SimpleWordDto(aWord,false),naverWordDto );
        }
    }

    // 네이버 검색 결과 얻기
    // 네이버 백과사전 api 사용
    public NaverWordDto getNaverSearch(String keyword) throws JsonProcessingException {
        String url = NAVER_API_URL + "?query="+ keyword;
        String clientId = naverAppProperties.getClientId();
        String clientSecret = naverAppProperties.getClientSecret();

        WebClient wc = WebClient.create(url);
        JsonNode response = wc.get()
                .uri(url)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .mapNotNull(jsonNode -> {
                    JsonNode items = jsonNode.get("items");
                    if (items != null && items.isArray()) {
                        for (JsonNode item : items) {
                            String link = item.get("link").asText();
                            if (link != null && link.startsWith("https://terms.naver.com")) {
                                return item;
                            }
                        }
                    }
                    return null;
                })
                .block();



        NaverWordDto naverWordDto = NaverWordDto.builder()
                    .node(response)
                    .fullDescription(crawlingDesc(response.get("link").asText()))
                    .build();
        return naverWordDto;

    }

    //크롤링 커넥션
    private String crawlingDesc(String url){
        Connection conn = Jsoup.connect(url);
        //Jsoup 커넥션 생성

        Document document = null;
        try {
            document = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String description = getDescripton(document);
        if(description==null){
            throw new ApiRequestException("description이 존재하지 않습니다.");
        }
        return description;
    }

    // 네이버 백과사전 api를 이용해 지식백과 url을 통해 용어 해설 추출
    private String getDescripton(Document document) {
        List<String> list = new ArrayList<>();

        //select 메서드 안에 css selector를 작성하여 Elements를 가져옴
        Elements selects = document.select("meta[property]");

        for (Element meta : selects) {
            if (meta.attr("property").equals("og:description")) {
                return meta.attr("content");
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Page<SimpleWordDto> getSearchWordList(String searchKeyword, Pageable pageable){
        Page<Word> allSearchWords  = wordRepository.findByWordContaining(searchKeyword, pageable);
        Page<SimpleWordDto> searchWordDtos = allSearchWords.map(SimpleWordDto::new);

        return searchWordDtos;
    }
}

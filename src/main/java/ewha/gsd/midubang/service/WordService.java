package ewha.gsd.midubang.service;

import ewha.gsd.midubang.dto.response.*;
import ewha.gsd.midubang.entity.Member;
import ewha.gsd.midubang.entity.MemberWord;
import ewha.gsd.midubang.entity.Word;
import ewha.gsd.midubang.exception.ApiRequestException;
import ewha.gsd.midubang.repository.MemberRepository;
import ewha.gsd.midubang.repository.MemberWordRepository;
import ewha.gsd.midubang.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;



@Service
@Slf4j
@RequiredArgsConstructor
public class WordService {
    private final MemberRepository memberRepository;
    private final WordRepository wordRepository;
    private final MemberWordRepository memberWordRepository;

    @Transactional
    public MemberWord addWord(Long member_id, Long word_id){
        Member member = memberRepository.findById(member_id).orElseThrow(()-> new ApiRequestException("user not found"));
        if(!wordRepository.exitsInMyDict(member_id, word_id)){
            Word word = wordRepository.findWordById(word_id);
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
    public void deleteWord(Long member_id, Long word_id){
        Member member = memberRepository.findById(member_id).orElseThrow(()-> new ApiRequestException("user not found"));
        wordRepository.deleteWord(member_id, word_id);
    }
    @Transactional(readOnly = true)
    public Page<SimpleWordDto> getWordList(Pageable pageable){
        Page<Word> allWords = wordRepository.findAll(pageable);
        Page<SimpleWordDto> wordList = allWords.map(SimpleWordDto::new);
        return wordList;
    }
    @Transactional(readOnly = true)

    public Page<WordDto> getMyWordList(Long member_id, Pageable pageable){
        Page<MemberWord> allWords  = wordRepository.findAllByMemberId(member_id, pageable);
        Page<WordDto> myWordList = allWords.map(WordDto::new);

        return myWordList;

    }
    @Transactional(readOnly = true)

    public WordDto getWord(Long member_id, Long word_id){
        MemberWord memberWord = wordRepository.findMyWord(member_id, word_id);
        if(memberWord==null){
            throw new ApiRequestException("word not exist");
        }
        return new WordDto(memberWord);
    }
    @Transactional(readOnly = true)

    public SimpleWordDto getAWord(Long word_id, Long member_id){
        Word aWord = wordRepository.findWordById(word_id);
        if(aWord==null){
            throw new ApiRequestException("word not exist");
        }
        if(wordRepository.exitsInMyDict(member_id, word_id)){
            return new SimpleWordDto(aWord,true);
        }else{
            return new SimpleWordDto(aWord, false);
        }
    }
    @Transactional(readOnly = true)

    public Page<SearchWordDto> getSearchWordList(String searchKeyword, Pageable pageable){
        Page<Word> allSearchWords  = wordRepository.findByWordContaining(searchKeyword, pageable);
        Page<SearchWordDto> searchWordDtos = allSearchWords.map(SearchWordDto::new);

        return searchWordDtos;
    }
}

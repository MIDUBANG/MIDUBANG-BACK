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

    public WordDto getWord(Long memberId, Long wordId){
        MemberWord memberWord = wordRepository.findMyWord(memberId, wordId);
        if(memberWord==null){
            throw new ApiRequestException("word not exist");
        }
        return new WordDto(memberWord);
    }
    @Transactional(readOnly = true)

    public SimpleWordDto getAWord(Long wordId, Long memberId){
        Word aWord = wordRepository.findWordById(wordId);
        if(aWord==null){
            throw new ApiRequestException("word not exist");
        }
        if(wordRepository.exitsInMyDict(memberId, wordId)){
            return new SimpleWordDto(aWord,true);
        }else{
            return new SimpleWordDto(aWord, false);
        }
    }
    @Transactional(readOnly = true)
    public Page<SimpleWordDto> getSearchWordList(String searchKeyword, Pageable pageable){
        Page<Word> allSearchWords  = wordRepository.findByWordContaining(searchKeyword, pageable);
        Page<SimpleWordDto> searchWordDtos = allSearchWords.map(SimpleWordDto::new);

        return searchWordDtos;
    }
}

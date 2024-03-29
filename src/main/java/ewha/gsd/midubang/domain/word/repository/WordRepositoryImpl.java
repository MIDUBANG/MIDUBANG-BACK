package ewha.gsd.midubang.domain.word.repository;

import ewha.gsd.midubang.domain.word.entity.MemberWord;
import ewha.gsd.midubang.domain.word.entity.Word;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.ArrayList;
import java.util.List;

import static ewha.gsd.midubang.domain.word.entity.QMemberWord.*;
import static ewha.gsd.midubang.domain.word.entity.QWord.*;



@RequiredArgsConstructor
public class WordRepositoryImpl implements WordRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public boolean exitsInMyDict(Long memberId, Long wordId) {
        Integer fetchOne = queryFactory.selectOne()
                .from(memberWord)
                .where(memberWord.member.memberId.eq(memberId)
                        .and(memberWord.word.wordId.eq(wordId)))
                .fetchFirst();

        return fetchOne != null;
    }

    public Word findWordById(Long wordId){
        return queryFactory.selectFrom(word1)
                .where(word1.wordId.eq(wordId))
                .fetchFirst();

    }

    public void deleteWord(Long memberId, Long wordId){
        long count = queryFactory
                .delete(memberWord)
                .where(memberWord.member.memberId.eq(memberId)
                        .and(memberWord.word.wordId.eq(wordId)))
                .execute();
    }

    public Page<MemberWord> findAllByMemberId(Long memberId, Pageable pageable){
        List<MemberWord> findAllWords = queryFactory.selectFrom(memberWord)
                .leftJoin(memberWord.word, word1)
                .where(memberWord.member.memberId.eq(memberId))
                .orderBy(memberWord.word_date.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return PageableExecutionUtils.getPage(findAllWords, pageable, findAllWords::size);

    }

    public MemberWord findMyWord(Long memberId, Long wordId){
        MemberWord myword = queryFactory.selectFrom(memberWord)
                .leftJoin(memberWord.word, word1)
                .where(memberWord.member.memberId.eq(memberId)
                        .and(memberWord.word.wordId.eq(wordId)))
                .fetchOne();

        return myword;
    }

    public List<Word> findWordsById(List<Long> ids){
        List<Word> words = new ArrayList<>();
        for(Long id : ids){
            if (id == null)
                continue;
            words.add(queryFactory.selectFrom(word1)
                    .from(word1)
                    .where(word1.wordId.eq(id))
                    .fetchFirst());
        }

        return words;
    }

//    public Page<SearchWordDto> getSearchWordDtos(String searchKeyword, Pageable pageable){
//        List<SearchWordDto> searchWords = queryFactory
//                .select(new QSearchWordDto(word1.wordId, word1.word))
//                .from(word1)
//                .where(containsSearch(searchKeyword))
//                .orderBy(word1.word.asc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        return PageableExecutionUtils.getPage(searchWords, pageable, searchWords::size);
//
//    }
//
//    private BooleanExpression containsSearch(String searchKeyword){
//        return searchKeyword != null ? word1.word.contains(searchKeyword) : null;
//    }

}

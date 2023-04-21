package ewha.gsd.midubang.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import ewha.gsd.midubang.dto.response.SearchWordDto;
import ewha.gsd.midubang.entity.MemberWord;
import ewha.gsd.midubang.entity.Word;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.ArrayList;
import java.util.List;

import static ewha.gsd.midubang.entity.QMemberWord.*;
import static ewha.gsd.midubang.entity.QWord.*;



@RequiredArgsConstructor
public class WordRepositoryImpl implements WordRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public boolean exitsInMyDict(Long member_id, Long word_id) {
        Integer fetchOne = queryFactory.selectOne()
                .from(memberWord)
                .where(memberWord.member.member_id.eq(member_id)
                        .and(memberWord.word.word_id.eq(word_id)))
                .fetchFirst();

        return fetchOne != null;
    }

    public Word findWordById(Long word_id){
        return queryFactory.selectFrom(word1)
                .where(word1.word_id.eq(word_id))
                .fetchFirst();

    }

    public void deleteWord(Long member_id, Long word_id){
        long count = queryFactory
                .delete(memberWord)
                .where(memberWord.member.member_id.eq(member_id)
                        .and(memberWord.word.word_id.eq(word_id)))
                .execute();
    }

    public Page<MemberWord> findAllByMemberId(Long member_id, Pageable pageable){
        List<MemberWord> findAllWords = queryFactory.selectFrom(memberWord)
                .leftJoin(memberWord.word, word1)
                .where(memberWord.member.member_id.eq(member_id))
                .orderBy(memberWord.word_date.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return PageableExecutionUtils.getPage(findAllWords, pageable, findAllWords::size);

    }

    public MemberWord findMyWord(Long member_id, Long word_id){
        MemberWord myword = queryFactory.selectFrom(memberWord)
                .leftJoin(memberWord.word, word1)
                .where(memberWord.member.member_id.eq(member_id)
                        .and(memberWord.word.word_id.eq(word_id)))
                .fetchOne();

        return myword;
    }

    public List<Word> findWordsById(List<Long> ids){
        List<Word> words = new ArrayList<>();
        for(Long id : ids){
            words.add(queryFactory.selectFrom(word1)
                    .from(word1)
                    .where(word1.word_id.eq(id))
                    .fetchFirst());
        }

        return words;
    }

//    public Page<SearchWordDto> getSearchWordDtos(String searchKeyword, Pageable pageable){
//        List<SearchWordDto> searchWords = queryFactory
//                .select(new QSearchWordDto(word1.word_id, word1.word))
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

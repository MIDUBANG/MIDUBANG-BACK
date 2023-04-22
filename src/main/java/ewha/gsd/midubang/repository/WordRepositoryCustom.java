package ewha.gsd.midubang.repository;

import ewha.gsd.midubang.entity.MemberWord;
import ewha.gsd.midubang.entity.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WordRepositoryCustom {
    boolean exitsInMyDict(Long memberId, Long wordId);
    Word findWordById(Long wordId);
    void deleteWord(Long memberId, Long wordId);
    Page<MemberWord> findAllByMemberId(Long memberId, Pageable pageable);
    MemberWord findMyWord(Long memberId, Long wordId);
    List<Word> findWordsById(List<Long> ids);
}

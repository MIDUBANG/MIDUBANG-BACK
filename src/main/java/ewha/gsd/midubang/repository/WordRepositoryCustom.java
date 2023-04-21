package ewha.gsd.midubang.repository;

import ewha.gsd.midubang.entity.MemberWord;
import ewha.gsd.midubang.entity.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WordRepositoryCustom {
    boolean exitsInMyDict(Long member_id, Long word_id);
    Word findWordById(Long word_id);
    void deleteWord(Long member_id, Long word_id);
    Page<MemberWord> findAllByMemberId(Long member_id, Pageable pageable);
    MemberWord findMyWord(Long member_id, Long word_id);
    List<Word> findWordsById(List<Long> ids);
}

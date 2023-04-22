package ewha.gsd.midubang.dto.response;

import ewha.gsd.midubang.entity.MemberWord;
import ewha.gsd.midubang.entity.Word;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class WordDto {
    private Long wordId;
    private String word;
    private String meaning;

    private LocalDateTime word_date;

    @Builder
    public WordDto(MemberWord memberWord){
        this.wordId = memberWord.getWord().getWordId();
        this.word = memberWord.getWord().getWord();
        this.meaning = memberWord.getWord().getMeaning();
        this.word_date = memberWord.getWord_date();
    }



}

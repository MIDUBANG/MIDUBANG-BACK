package ewha.gsd.midubang.dto.response;


import ewha.gsd.midubang.entity.MemberWord;
import ewha.gsd.midubang.entity.Word;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleWordDto {
    private  Long wordId;
    private String word;
    private String meaning;

    private Boolean isSaved;

    @Builder
    public SimpleWordDto(Word word, Boolean isSaved){
        this.wordId = word.getWordId();
        this.word = word.getWord();
        this.meaning = word.getMeaning();
        this.isSaved = isSaved;

    }

    @Builder
    public SimpleWordDto(Word word){
        this.wordId=word.getWordId();
        this.word = word.getWord();
        this.meaning = word.getMeaning();

    }
}

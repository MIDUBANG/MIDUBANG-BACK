package ewha.gsd.midubang.domain.word.dto;

import ewha.gsd.midubang.domain.word.entity.Word;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchWordDto {
    private  Long wordId;
    private String word;

    public SearchWordDto(Word word){
        this.wordId = word.getWordId();
        this.word= word.getWord();
    }
}

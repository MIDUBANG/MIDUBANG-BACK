package ewha.gsd.midubang.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import ewha.gsd.midubang.entity.Word;
import lombok.AccessLevel;
import lombok.Builder;
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

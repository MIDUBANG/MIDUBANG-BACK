package ewha.gsd.midubang.dto.response;


import ewha.gsd.midubang.entity.MemberWord;
import ewha.gsd.midubang.entity.Word;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleWordDto {
    private  Long word_id;
    private String word;
    private String meaning;

    @Builder
    public SimpleWordDto(Word word){
        this.word_id = word.getWord_id();
        this.word = word.getWord();
        this.meaning = word.getMeaning();

    }
}

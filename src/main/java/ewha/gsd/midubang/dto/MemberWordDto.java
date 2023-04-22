
package ewha.gsd.midubang.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberWordDto {
    private String email;
    private Long wordId;
    private String word;
    private String meaning;

    @Builder
    public MemberWordDto(String email, Long wordId, String word, String meaning){
        this.email = email;
        this.wordId=wordId;
        this.word = word;
        this.meaning = meaning;
    }
}
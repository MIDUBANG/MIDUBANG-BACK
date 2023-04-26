package ewha.gsd.midubang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionListDto {
    private Long questionId;
    private String question;
    private Integer numOfAnswers;
}

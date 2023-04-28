package ewha.gsd.midubang.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDetailDto {
    private Long questionId;
    private String question;
    private String createdDate;
    private List<CommentListDto> answers =  new ArrayList<>();
}

package ewha.gsd.midubang.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TodayResponseDto {
    private List<QuestionListDto> questionList;
    private List<PostListDto> postList;
}

package ewha.gsd.midubang.dto.response;

import ewha.gsd.midubang.dto.PostListDto;
import ewha.gsd.midubang.dto.QuestionListDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TodayResponseDto {
    private List<QuestionListDto> questionList;
    private List<PostListDto> postList;
}

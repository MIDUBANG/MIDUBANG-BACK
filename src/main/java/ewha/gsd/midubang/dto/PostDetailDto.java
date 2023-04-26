package ewha.gsd.midubang.dto;

import ewha.gsd.midubang.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailDto {
    private Long postId;
    private String writer;
    private String title;
    private String detail;
    private String createdDate;
    private List<CommentListDto> comments = new ArrayList<>();
}

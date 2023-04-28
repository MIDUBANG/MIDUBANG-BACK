package ewha.gsd.midubang.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentListDto {
    private Long commentId;
    private String writer;
    private String comment;
    private String createdDate;
}

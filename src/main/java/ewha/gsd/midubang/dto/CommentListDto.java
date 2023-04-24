package ewha.gsd.midubang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentListDto {
    private String writer;
    private String comment;
    private String createdDate;
}

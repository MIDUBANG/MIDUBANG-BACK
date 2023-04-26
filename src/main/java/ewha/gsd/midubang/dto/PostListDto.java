package ewha.gsd.midubang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostListDto {
    private Long postId;
    private String writer;
    private String title;
    private String content;
    private Integer numOfComments;
}

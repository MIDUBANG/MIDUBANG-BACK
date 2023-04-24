package ewha.gsd.midubang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostListDto {
    private String writer;
    private String title;
    private Integer numOfComments;
}

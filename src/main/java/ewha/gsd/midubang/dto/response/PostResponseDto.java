package ewha.gsd.midubang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class PostResponseDto {
    private HttpStatus httpStatus;
    private Long postId;
}

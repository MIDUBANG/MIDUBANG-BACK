package ewha.gsd.midubang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CheckCountResponseDto {
    private HttpStatus httpStatus;
    private Integer count;

}

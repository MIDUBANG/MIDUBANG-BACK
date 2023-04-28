package ewha.gsd.midubang.domain.checklist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CheckCountResponseDto {
    private HttpStatus httpStatus;
    private Integer count;

}

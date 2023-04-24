package ewha.gsd.midubang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class IdDto {
    private HttpStatus httpStatus;
    private Long id;
}

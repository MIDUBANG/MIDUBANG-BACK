package ewha.gsd.midubang.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DeleteResponseDto {
    private HttpStatus status;
    private String message;
}

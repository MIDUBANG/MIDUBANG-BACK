package ewha.gsd.midubang.domain.community.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DeleteResponseDto {
    private HttpStatus status;
    private String message;
}

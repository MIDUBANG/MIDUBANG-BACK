package ewha.gsd.midubang.domain.checklist.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ChecksResponseDto {
    private HttpStatus httpStatus;
    private List<Integer> checklist;

    public ChecksResponseDto(HttpStatus httpStatus, List<Integer> checklist) {
        this.httpStatus = httpStatus;
        this.checklist = checklist;
    }
}

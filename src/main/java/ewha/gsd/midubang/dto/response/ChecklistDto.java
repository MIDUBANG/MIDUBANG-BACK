package ewha.gsd.midubang.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ChecklistDto {
    private HttpStatus httpStatus;
    private List<Integer> checklist;

    public ChecklistDto(HttpStatus httpStatus, List<Integer> checklist) {
        this.httpStatus = httpStatus;
        this.checklist = checklist;
    }
}

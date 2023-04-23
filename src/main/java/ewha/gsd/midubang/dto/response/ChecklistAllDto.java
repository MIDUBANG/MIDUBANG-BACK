package ewha.gsd.midubang.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ChecklistAllDto {
    private HttpStatus httpStatus;
    private List<Integer> checklist;

    public ChecklistAllDto(HttpStatus httpStatus, List<Integer> checklist) {
        this.httpStatus = httpStatus;
        this.checklist = checklist;
    }
}

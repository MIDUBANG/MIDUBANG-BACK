package ewha.gsd.midubang.dto.response;

import ewha.gsd.midubang.dto.ChecklistDto;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ChecklistResponseDto {
    private HttpStatus httpStatus;
    private Integer categoryId;
    private List<ChecklistDto> checklist;
    private List<Integer> userCheck;

    public ChecklistResponseDto(HttpStatus httpStatus, Integer categoryId,
                                List<ChecklistDto> checklist, List<Integer> userCheck) {
        this.httpStatus = httpStatus;
        this.categoryId = categoryId;
        this.checklist = checklist;
        this.userCheck = userCheck;
    }
}

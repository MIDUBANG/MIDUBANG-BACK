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
    private List<Integer> isCheck;
}

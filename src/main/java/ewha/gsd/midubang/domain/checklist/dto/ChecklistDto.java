package ewha.gsd.midubang.domain.checklist.dto;

import lombok.Data;

@Data
public class ChecklistDto {
    private Integer checklistId;
    private String listName;
    private String listDetail;
}

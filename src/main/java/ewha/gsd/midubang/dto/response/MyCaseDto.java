package ewha.gsd.midubang.dto.response;



import com.querydsl.core.annotations.QueryProjection;
import ewha.gsd.midubang.entity.CaseType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyCaseDto {
    private Long case_id;
    private String desc;
    private CaseType caseType;
    private Boolean case_exists;

    private List<Long> word_ref;



    @Builder

    public MyCaseDto(Long case_id, String desc, CaseType caseType, Boolean case_exists, List<Long> word_ref){
        this.case_id = case_id;
        this.desc = desc;
        this.caseType = caseType;
        this.case_exists = case_exists;
        this.word_ref = word_ref;
    }


}

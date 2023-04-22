package ewha.gsd.midubang.dto.response;



import com.querydsl.core.annotations.QueryProjection;
import ewha.gsd.midubang.entity.CaseType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyCaseDto {
    private Long caseId;

    private String  case_detail;
    private String desc;
    private String article_url;
    private CaseType caseType;
    private Boolean case_exists;
    private String raw_case;

    private List<Long> word_ref;



    @Builder
    public MyCaseDto(Long caseId,String case_detail, String desc, String article_url, CaseType caseType, Boolean case_exists, String raw_case, List<Long> word_ref){
        this.caseId = caseId;
        this.case_detail = case_detail;
        this.desc = desc;
        this.article_url = article_url;
        this.caseType = caseType;
        this.case_exists = case_exists;
        this.raw_case = raw_case;
        this.word_ref = word_ref;
    }


}

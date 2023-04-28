package ewha.gsd.midubang.domain.analysis.dto;

import ewha.gsd.midubang.domain.analysis.entity.ContractType;
import lombok.Data;

import java.util.List;

@Data
public class RecordReqDto {
    private Integer commission;
    private Integer answer_commission;

    private Boolean is_expensive;
    private ContractType contract_type;
    private String image_url;
    private List<RawCaseDto> inclusions;
    private List<Long> omissions;

}

package ewha.gsd.midubang.dto;

import ewha.gsd.midubang.dto.response.RawCaseDto;
import ewha.gsd.midubang.entity.ContractType;
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

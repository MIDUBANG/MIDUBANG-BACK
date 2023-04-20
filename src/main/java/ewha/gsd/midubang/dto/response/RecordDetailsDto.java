package ewha.gsd.midubang.dto.response;

import ewha.gsd.midubang.entity.ContractType;
import ewha.gsd.midubang.entity.Record;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
public class RecordDetailsDto {
    private Long record_id;
    private Boolean is_expensive;
    private Integer commission;
    private Integer answer_commission;
    private ContractType contract_type;
    private String image_url;

    @Builder
    public RecordDetailsDto(Record record) {
        this.record_id = record.getRecord_id();
        this.is_expensive = record.getIs_expensive();
        this.commission = record.getCommission();
        this.answer_commission = record.getAnswer_commission();
        this.contract_type = record.getContract_type();
        this.image_url = record.getImage_url();
    }
}

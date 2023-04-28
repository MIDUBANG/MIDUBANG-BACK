package ewha.gsd.midubang.domain.analysis.dto;

import ewha.gsd.midubang.domain.analysis.entity.ContractType;
import ewha.gsd.midubang.domain.analysis.entity.Record;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RecordDetailsDto {
    private Long recordId;
    private Boolean is_expensive;
    private Integer commission;
    private Integer answer_commission;
    private ContractType contract_type;
    private String image_url;

    private LocalDate record_date;

    @Builder
    public RecordDetailsDto(Record record) {
        this.recordId = record.getRecordId();
        this.is_expensive = record.getIs_expensive();
        this.commission = record.getCommission();
        this.answer_commission = record.getAnswer_commission();
        this.contract_type = record.getContract_type();
        this.image_url = record.getImage_url();
        this.record_date = record.getRecord_date();
    }
}

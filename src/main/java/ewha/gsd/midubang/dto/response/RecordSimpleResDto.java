package ewha.gsd.midubang.dto.response;

import ewha.gsd.midubang.entity.ContractType;
import ewha.gsd.midubang.entity.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class RecordSimpleResDto {
    private Long recordId;
    private ContractType contract_type;
    private Boolean is_expensive;
    private Integer commission;
    private Integer answer_commission;

    private String image_url;
    private LocalDate record_date;

    public  RecordSimpleResDto(Record record){
        this.recordId = record.getRecordId();
        this.contract_type = record.getContract_type();
        this.is_expensive = record.getIs_expensive();
        this.commission = record.getCommission();
        this.answer_commission = record.getAnswer_commission();
        this.image_url = record.getImage_url();
        this.record_date = record.getRecord_date();
    }





}

package ewha.gsd.midubang.dto.response;

import ewha.gsd.midubang.entity.ContractType;
import ewha.gsd.midubang.entity.Record;
import ewha.gsd.midubang.entity.RecordCase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class RecordResDto {
    private RecordDetailsDto record;
    private List<MyCaseDto> myCaseDto;

    private List<SimpleWordDto> simpleWordDtos;


    public RecordResDto( RecordDetailsDto record, List<MyCaseDto> myCaseDto, List<SimpleWordDto> simpleWordDtos){
        this.record = record;
        this.myCaseDto = myCaseDto;
        this.simpleWordDtos= simpleWordDtos;
    }



}

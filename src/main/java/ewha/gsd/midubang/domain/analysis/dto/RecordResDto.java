package ewha.gsd.midubang.domain.analysis.dto;

import ewha.gsd.midubang.domain.word.dto.SimpleWordDto;
import lombok.Getter;

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

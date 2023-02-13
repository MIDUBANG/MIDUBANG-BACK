package ewha.gsd.midubang.service;

import com.querydsl.core.Tuple;
import ewha.gsd.midubang.dto.RecordReqDto;
import ewha.gsd.midubang.dto.response.*;
import ewha.gsd.midubang.entity.*;
import ewha.gsd.midubang.exception.ApiRequestException;
import ewha.gsd.midubang.jwt.TokenProvider;
import ewha.gsd.midubang.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ewha.gsd.midubang.entity.QRecordCase.recordCase;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class AnalysisService {
    private final CaseRepository caseRepository;
    private final RecordCaseRepository recordCaseRepository;
    private final MemberRepository memberRepository;
    private final RecordRepository recordRepository;
    private final WordRepository wordRepository;

    public Long saveRecord(Long user_id, RecordReqDto recordReqDto){
        Member member = memberRepository.findById(user_id).orElseThrow(()-> new ApiRequestException("user not found"));
        LocalDate today = LocalDate.now();
        Record record = Record.builder()
                .is_expensive(recordReqDto.getIs_expensive())
                .answer_commission(recordReqDto.getCommission())
                .commission(recordReqDto.getAnswer_commission())
                .record_date(today)
                .contract_type(recordReqDto.getContract_type())
                .image_url(recordReqDto.getImage_url())
                .build();

        record.setMember(member);
        recordRepository.save(record);



        for(Long i : recordReqDto.getInclusions()){
            Case aCase = caseRepository.findCaseById(i);

            log.info("found case: " + aCase);

            RecordCase recordCase = RecordCase.builder()
                    .record(record)
                    .aCase(aCase)
                    .case_exists(true)
                    .build();

            recordCaseRepository.save(recordCase);
        }

        for(Long i: recordReqDto.getOmissions()){
            Case aCase = caseRepository.findCaseById(i);

            RecordCase recordCase = RecordCase.builder()
                    .record(record)
                    .aCase(aCase)
                    .case_exists(false)
                    .build();

            recordCaseRepository.save(recordCase);
        }

        return record.getRecord_id();

    }



    public RecordListResDto getRecordList(Long member_id){
        List<Record> recordList = recordRepository.findRecordListByMemberId(member_id);
        List<RecordSimpleResDto> simpleRecordList = recordList.stream().map(h -> new RecordSimpleResDto(h)).collect(Collectors.toList());

        RecordListResDto result = new RecordListResDto(simpleRecordList);
        if(result.getRecordList().isEmpty()){
            result.setNoRecord(true);
        }
        return result;
    }

    public RecordResDto getRecord(Long record_id){
        Record record = recordRepository.findRecordById(record_id);
        if(record==null){
            throw new ApiRequestException("record_id not exist");
        }
        List<Tuple> caseList = recordCaseRepository.findAllRecordCasesById(record_id);
        RecordResDto recordResDto = makeRecordResDto(record, caseList);

        return recordResDto;
    }

    public void deleteRecord(Long record_id){
        recordCaseRepository.deleteRecordCase(record_id);
        recordRepository.deleteRecord(record_id);

    }

    public List<Long> strToLongList(String str){
        long[] noArray = Stream.of(str.split(",")).mapToLong(Long::parseLong).toArray();
        Long[] noListBoxed = ArrayUtils.toObject(noArray);
        List<Long> noList = Arrays.asList(noListBoxed);

        return noList;
    }

    public RecordResDto makeRecordResDto(Record record, List<Tuple> tuple){
        List<MyCaseDto> myCaseDtoList = new ArrayList<>();
        List<Long> wordList = new ArrayList<>();


        for(Tuple t : tuple){
            String str = t.get(recordCase.aCase.word_ref);
            List<Long> noList = strToLongList(str);
            wordList.addAll(noList);
            MyCaseDto myCaseDto = MyCaseDto.builder()
                    .case_id(t.get(recordCase.aCase.id))
                    .desc(t.get(recordCase.aCase.desc))
                    .caseType(t.get(recordCase.aCase.type))
                    .case_exists(t.get(recordCase.case_exists))
                    .word_ref(noList)
                    .build();

            myCaseDtoList.add(myCaseDto);

        }

        List<Long> newList = wordList.stream().distinct().collect(Collectors.toList());
        List<SimpleWordDto> simpleWordDtoList = wordRepository.findWordsById(newList).stream()
                .map(h-> new SimpleWordDto(h)).collect(Collectors.toList());

        simpleWordDtoList.stream().sorted(Comparator.comparing(SimpleWordDto::getWord_id)).collect(Collectors.toList());

        return new RecordResDto(record, myCaseDtoList, simpleWordDtoList);
    }
}

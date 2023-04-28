package ewha.gsd.midubang.domain.analysis.controller;


import ewha.gsd.midubang.domain.analysis.service.AnalysisService;
import ewha.gsd.midubang.domain.analysis.dto.RecordReqDto;
import ewha.gsd.midubang.domain.member.dto.UserInfoDto;
import ewha.gsd.midubang.domain.analysis.dto.RecordListResDto;
import ewha.gsd.midubang.domain.analysis.dto.RecordResDto;
import ewha.gsd.midubang.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/analysis")
@Slf4j
public class AnalysisController {
    private final TokenProvider tokenProvider;
    private final AnalysisService analysisService;

    //특약 저장
    //request에 포함 :pet, commision, loan, substitute, contract_type, image_url, 포함된 특약, 빠진 특약
    // response에 포함: 빠진 특약에 대한 설명과 예시 (테이블에 예시를 따로 컬럼을 만들지 않아서..일단 설명에 포함된 걸로 치고 작업)
    @PostMapping("")
    public ResponseEntity<RecordResDto> saveRecord(HttpServletRequest request, @RequestBody RecordReqDto recordReqDto){
        UserInfoDto userInfoDto = tokenProvider.getUserInfoByRequest(request);
        Long recordId = analysisService.saveRecord(userInfoDto.getMemberId(), recordReqDto );
        RecordResDto recordResDto = analysisService.getRecord(recordId);
        return ResponseEntity.status(HttpStatus.OK).body(recordResDto);

    }

    //저장한 특약 레코드 목록 가져오기
    @GetMapping("/list")
    public ResponseEntity<RecordListResDto> getRecordList(HttpServletRequest request){
        UserInfoDto userInfoDto = tokenProvider.getUserInfoByRequest(request);
        RecordListResDto result = analysisService.getRecordList(userInfoDto.getMemberId());
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    //특정한 특약 레코드 가져오기
    @GetMapping("")
    public ResponseEntity<RecordResDto> getRecord(@RequestParam Long recordId){
        RecordResDto recordResDto = analysisService.getRecord(recordId);
        return ResponseEntity.status(HttpStatus.OK).body(recordResDto);
    }

    //특약 삭제
    @DeleteMapping("")
    public ResponseEntity<Objects> deleteRecord(@RequestParam Long recordId){
        analysisService.deleteRecord(recordId);
        return ResponseEntity.ok().build();
    }
}

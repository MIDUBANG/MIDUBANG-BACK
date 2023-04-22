package ewha.gsd.midubang.controller;

import ewha.gsd.midubang.dto.Message;
import ewha.gsd.midubang.dto.response.ChecklistDto;
import ewha.gsd.midubang.jwt.TokenProvider;
import ewha.gsd.midubang.service.ChecklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checklist")
@Slf4j
public class ChecklistController {

    private final TokenProvider tokenProvider;
    private final ChecklistService checklistService;

    /* 체크리스트 항목 check */
    @PostMapping("/{checklistId}")
    public ResponseEntity saveChecklist(HttpServletRequest request, @PathVariable Integer checklistId) {
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMember_id();
        if (!checklistService.saveChecklist(memberId, checklistId)) {
            return ResponseEntity.ok(
                    new Message(HttpStatus.CONFLICT,
                    "이미 체크되어 있는 항목입니다.")
            );
        }
        return ResponseEntity.ok(
                new Message(HttpStatus.OK,
                        null)
        );
    }

    /* 체크리스트 항목 check 해제 */
    @DeleteMapping("/{checklistId}")
    public ResponseEntity deleteChecklist(HttpServletRequest request, @PathVariable Integer checklistId) {
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMember_id();
        if (!checklistService.deleteChecklist(memberId, checklistId)) {
            return ResponseEntity.ok(
                    new Message(HttpStatus.NOT_FOUND,
                            "대상 항목을 찾을 수 없습니다.")
            );
        }
        return ResponseEntity.ok(
                new Message(HttpStatus.OK,
                        null)
        );
    }

    /* 유저의 체크 항목 불러오기 */
    @GetMapping("/all")
    public ResponseEntity getAllChecklist(HttpServletRequest request) {
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMember_id();
        return ResponseEntity.ok(
                new ChecklistDto(
                        HttpStatus.OK,
                        checklistService.getAllChecklist(memberId)
                )
        );
    }
}

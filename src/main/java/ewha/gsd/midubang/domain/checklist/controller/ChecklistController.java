package ewha.gsd.midubang.domain.checklist.controller;

import ewha.gsd.midubang.domain.checklist.service.ChecklistService;
import ewha.gsd.midubang.global.dto.Message;
import ewha.gsd.midubang.domain.checklist.dto.CheckCountResponseDto;
import ewha.gsd.midubang.domain.checklist.dto.ChecksResponseDto;
import ewha.gsd.midubang.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMemberId();
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
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMemberId();
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

    /* 유저의 체크 항목 불러오기 (전체) */
    @GetMapping("/all/check")
    public ResponseEntity getAllChecklist(HttpServletRequest request) {
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMemberId();
        return ResponseEntity.ok(
                new ChecksResponseDto(
                        HttpStatus.OK,
                        checklistService.getAllChecklist(memberId)
                )
        );
    }

    /* 유저의 체크 항목 불러오기 (카테고리별) */
    @GetMapping("/{categoryId}/check")
    public ResponseEntity getChecksByCategoryId(HttpServletRequest request, @PathVariable Integer categoryId) {
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMemberId();
        return ResponseEntity.ok(
                new ChecksResponseDto(
                        HttpStatus.OK,
                        checklistService.getChecksByCategoryId(memberId, categoryId)
                )
        );
    }

    /* 체크리스트 가져오기 (카테고리별) + 체크 여부 */
    @GetMapping("/{categoryId}")
    public ResponseEntity getChecklistByCategoryId(HttpServletRequest request, @PathVariable Integer categoryId) {
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMemberId();
        return ResponseEntity.ok(checklistService.getChecklistByCategoryId(memberId, categoryId));
    }

    /* 유저의 체크 항목 개수 가져오기 (전체) */
    @GetMapping("/count")
    public ResponseEntity getAllCheckCount(HttpServletRequest request) {
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMemberId();
        return ResponseEntity.ok(
                new CheckCountResponseDto(
                        HttpStatus.OK,
                        checklistService.getAllCheckCount(memberId)
                )
        );
    }

    /* 유저의 체크 항목 개수 가져오기 (카테고리별) */
    @GetMapping("/count/{categoryId}")
    public ResponseEntity getCheckCountByCategoryId(HttpServletRequest request, @PathVariable Integer categoryId) {
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMemberId();
        return ResponseEntity.ok(
                new CheckCountResponseDto(
                        HttpStatus.OK,
                        checklistService.getCheckCountByCategoryId(memberId, categoryId)
                )
        );
    }

    /* 체크리스트 전체 체크 (카테고리 단위) */
    @PostMapping("/category/{categoryId}")
    public ResponseEntity saveAllCheckByCategory(HttpServletRequest request, @PathVariable Integer categoryId) {
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMemberId();
        if (!checklistService.saveAllCheckByCategory(memberId, categoryId)) {
            return ResponseEntity.ok(
                    new Message(HttpStatus.CONFLICT,
                            "All data is already checked.")
            );
        }
        return ResponseEntity.ok(
                new Message(HttpStatus.OK,
                        null)
        );
    }

    /* 체크리스트 전체 체크 해제 (카테고리 단위) */
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity deleteAllCheckByCategory(HttpServletRequest request, @PathVariable Integer categoryId) {
        Long memberId = tokenProvider.getUserInfoByRequest(request).getMemberId();
        if (!checklistService.deleteAllCheckByCategory(memberId, categoryId)) {
            return ResponseEntity.ok(
                    new Message(HttpStatus.NOT_FOUND,
                            "No check data.")
            );
        }
        return ResponseEntity.ok(
                new Message(HttpStatus.OK,
                        null)
        );
    }

}

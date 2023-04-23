package ewha.gsd.midubang.service;

import ewha.gsd.midubang.dto.ChecklistDto;
import ewha.gsd.midubang.dto.response.ChecklistResponseDto;
import ewha.gsd.midubang.entity.Check;
import ewha.gsd.midubang.entity.ChecklistContent;
import ewha.gsd.midubang.repository.ChecklistContentRepository;
import ewha.gsd.midubang.repository.CheckQuerydslRepository;
import ewha.gsd.midubang.repository.CheckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChecklistService {

    private final CheckRepository checkRepository;
    private final CheckQuerydslRepository checkQuerydslRepository;
    private final ChecklistContentRepository checklistContentRepository;

    /* 체크리스트 항목 check */
    public Boolean saveChecklist(Long memberId, Integer checklistId) {

        if (checkQuerydslRepository.existCheckByMemberIdAndChecklistId(memberId, checklistId)) {
            return false;
        }

        ChecklistContent content = checklistContentRepository.findByChecklistId(checklistId);
        Check check = new Check(memberId, content);
        checkRepository.save(check);
        return true;
    }

    /* 체크리스트 항목 check 해제 */
    public Boolean deleteChecklist(Long memberId, Integer checklistId) {

        if (!checkQuerydslRepository.existCheckByMemberIdAndChecklistId(memberId, checklistId)) {
            return false;
        }
        checkQuerydslRepository.deleteCheckByMemberIdAndChecklistId(memberId, checklistId);
        return true;
    }

    /* 유저의 체크 항목 불러오기 */
    public List<Integer> getAllChecklist(Long memberId) {
        List<Integer> list = checkQuerydslRepository.findChecklistIdByMemberId(memberId);
        return list;
    }

    /* 유저의 체크 항목 불러오기 (카테고리별) */

    /* 체크리스트 가져오기 (카테고리별) + 체크 여부 */
    public ChecklistResponseDto getChecklistByCategory(Long memberId, Integer categoryId) {
        List<ChecklistDto> list = checkQuerydslRepository.findChecklistByCategoryId(categoryId);

        return null;
    }
}

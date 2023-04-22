package ewha.gsd.midubang.service;

import ewha.gsd.midubang.entity.Checklist;
import ewha.gsd.midubang.entity.ChecklistID;
import ewha.gsd.midubang.entity.Member;
import ewha.gsd.midubang.exception.ApiRequestException;
import ewha.gsd.midubang.repository.ChecklistQuerydslRepository;
import ewha.gsd.midubang.repository.ChecklistRepository;
import ewha.gsd.midubang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChecklistService {

    private final ChecklistRepository checklistRepository;
    private final ChecklistQuerydslRepository checklistQuerydslRepository;

    /* 체크리스트 항목 check */
    public Boolean saveChecklist(Long memberId, Integer checklistId) {
        if (checklistRepository.existsById(new ChecklistID(checklistId, memberId))) {
            return false;
        }
        Checklist checklist = new Checklist(checklistId, memberId);
        checklistRepository.save(checklist);
        return true;
    }

    /* 체크리스트 항목 check 해제 */
    public Boolean deleteChecklist(Long memberId, Integer checklistId) {
        Checklist checklist = new Checklist(checklistId, memberId);
        if (!checklistRepository.existsById(new ChecklistID(checklistId, memberId))) {
            return false;
        }
        checklistRepository.delete(checklist);
        return true;
    }

    /* 유저의 체크 항목 불러오기 */
    public List<Integer> getAllChecklist(Long memberId) {
        List<Integer> list = checklistQuerydslRepository.findChecklistIdByMemberId(memberId);
        return list;
    }
}

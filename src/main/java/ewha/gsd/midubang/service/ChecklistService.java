package ewha.gsd.midubang.service;

import ewha.gsd.midubang.dto.ChecklistDto;
import ewha.gsd.midubang.dto.response.ChecklistResponseDto;
import ewha.gsd.midubang.entity.Check;
import ewha.gsd.midubang.entity.ChecklistContent;
import ewha.gsd.midubang.repository.ChecklistContentQuerydslRepository;
import ewha.gsd.midubang.repository.ChecklistContentRepository;
import ewha.gsd.midubang.repository.CheckQuerydslRepository;
import ewha.gsd.midubang.repository.CheckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChecklistService {

    private final CheckRepository checkRepository;
    private final CheckQuerydslRepository checkQuerydslRepository;
    private final ChecklistContentRepository checklistContentRepository;
    private final ChecklistContentQuerydslRepository checklistContentQuerydslRepository;

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

    /* 유저의 체크 항목 불러오기 (전체) */
    public List<Integer> getAllChecklist(Long memberId) {
        List<Integer> list = checkQuerydslRepository.findChecksByMemberId(memberId);
        return list;
    }

    /* 유저의 체크 항목 불러오기 (카테고리별) */
    public List<Integer> getChecksByCategoryId(Long memberId, Integer categoryId) {
        List<Integer> userCheck = checkQuerydslRepository.findAllCheckIdByMemberIdAndCategoryId(memberId, categoryId);
        return userCheck;
    }

    /* 체크리스트 가져오기 (카테고리별) + 체크 여부 */
    public ChecklistResponseDto getChecklistByCategoryId(Long memberId, Integer categoryId) {
        List<ChecklistDto> checklist = checkQuerydslRepository.findChecklistByCategoryId(categoryId);
        List<Integer> userCheck = checkQuerydslRepository.findAllCheckIdByMemberIdAndCategoryId(memberId, categoryId);
        ChecklistResponseDto response = new ChecklistResponseDto(
                HttpStatus.OK,
                categoryId,
                checklist,
                userCheck
        );
        return response;
    }

    /* 유저의 체크 항목 개수 가져오기 (전체) */
    public Integer getAllCheckCount(Long memberId) {
        return checkQuerydslRepository.getAllCountByMemberId(memberId);
    }

    /* 유저의 체크 항목 개수 가져오기 (카테고리별) */
    public Integer getCheckCountByCategoryId(Long memberId, Integer categoryId) {
        return checkQuerydslRepository.getCountByMemberIdAndCategoryId(memberId, categoryId);
    }


    /* 체크리스트 전체 체크 (카테고리 단위) */
    public Boolean saveAllCheckByCategory(Long memberId, Integer categoryId) {
        List<Check> userCheck = checkQuerydslRepository.findAllCheckByMemberIdAndCategoryId(memberId, categoryId);
        List<ChecklistContent> contentList =
                checklistContentQuerydslRepository.findAllChecklistContentByCategoryId(categoryId);

        if (userCheck.size() == contentList.size())
            return false;

        List<Long> userCheckIdList = userCheck.stream()
                .map(m -> m.getCheckId())
                .collect(Collectors.toList());

        List<Check> newDataList = new ArrayList<>();
        for (ChecklistContent content : contentList) {
            if (userCheckIdList.contains(content.getCategoryId()))
                continue;

            Check check = new Check(memberId, content);

            newDataList.add(check);
        }

        checkRepository.saveAll(newDataList);
        return true;
    }


    /* 체크리스트 전체 체크 해제 (카테고리 단위) */
    public Boolean deleteAllCheckByCategory(Long memberId, Integer categoryId) {
        List<Check> userCheck = checkQuerydslRepository.findAllCheckByMemberIdAndCategoryId(memberId, categoryId);

        if (userCheck.isEmpty())
            return false;

        checkRepository.deleteAllInBatch(userCheck);
        return true;
    }

}

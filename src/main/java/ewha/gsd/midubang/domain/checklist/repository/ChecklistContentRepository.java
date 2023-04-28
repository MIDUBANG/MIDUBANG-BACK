package ewha.gsd.midubang.domain.checklist.repository;

import ewha.gsd.midubang.domain.checklist.entity.ChecklistContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistContentRepository extends JpaRepository<ChecklistContent, Integer> {
    ChecklistContent findByChecklistId(Integer checklistId);
}

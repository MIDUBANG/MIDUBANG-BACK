package ewha.gsd.midubang.repository;

import ewha.gsd.midubang.entity.ChecklistContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistContentRepository extends JpaRepository<ChecklistContent, Integer> {
    ChecklistContent findByChecklistId(Integer checklistId);
}

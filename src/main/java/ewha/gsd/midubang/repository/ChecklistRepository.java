package ewha.gsd.midubang.repository;

import ewha.gsd.midubang.entity.Checklist;
import ewha.gsd.midubang.entity.ChecklistID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, ChecklistID> {
}

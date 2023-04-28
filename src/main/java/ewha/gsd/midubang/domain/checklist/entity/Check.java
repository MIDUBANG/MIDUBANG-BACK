package ewha.gsd.midubang.domain.checklist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "checks")
public class Check {

    @Id
    @Column(name = "check_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkId;

    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "checklist_id")
    private ChecklistContent checklistContent;

    public Check(Long memberId, ChecklistContent checklistContent) {
        this.memberId = memberId;
        this.checklistContent = checklistContent;
    }
}

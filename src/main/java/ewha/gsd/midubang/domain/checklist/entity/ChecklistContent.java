package ewha.gsd.midubang.domain.checklist.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="checklist_content")
@NoArgsConstructor
public class ChecklistContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checklist_id")
    private Integer checklistId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ChecklistCategory categoryId;

    @Column(name = "list_name", nullable = false)
    private String listName;

    @Column(name = "list_detail")
    private String listDetail;
}

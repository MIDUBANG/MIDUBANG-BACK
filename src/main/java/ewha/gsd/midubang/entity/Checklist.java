package ewha.gsd.midubang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "checklist")
@IdClass(ChecklistID.class)
public class Checklist {

    @Id
    @Column(name = "checklist_id")
    private Integer checklistId;

    @Id
    @Column(name = "member_id")
    private Long memberId;
}

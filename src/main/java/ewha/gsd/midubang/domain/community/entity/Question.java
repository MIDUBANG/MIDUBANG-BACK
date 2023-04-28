package ewha.gsd.midubang.domain.community.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @Column
    private String content;

    @Column(name = "created_date")
    private String createdDate;

    @Column(name = "question_type")
    private Boolean questionType; // true: 진지, false: 엉뚱

    @JsonIgnore
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    private List<Answer> answers;


    public Question(String content, String createdDate, Boolean questionType, List<Answer> answers) {
        this.content = content;
        this.createdDate = createdDate;
        this.questionType = questionType;
        this.answers = answers;
    }
}

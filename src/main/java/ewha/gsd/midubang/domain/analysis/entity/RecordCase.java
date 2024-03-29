package ewha.gsd.midubang.domain.analysis.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="record_case")
@Getter
public class RecordCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "record_id",referencedColumnName = "record_id")
    private Record record;

    @ManyToOne
    @JoinColumn(name="case_id", referencedColumnName = "case_id")
    private Case aCase;

    @Column(name = "case_exists")
    private Boolean case_exists;

    private String raw_case;



    @Builder
    public RecordCase(Record record, Case aCase, Boolean case_exists, String raw_case){
        this.record = record;
        this.aCase = aCase;
        this.case_exists = case_exists;
        this.raw_case = raw_case;
    }





}
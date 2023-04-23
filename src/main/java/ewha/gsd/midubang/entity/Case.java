package ewha.gsd.midubang.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="cases")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Case {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="case_id")
    private Long id;

    private String case_detail;

    private String description;
    private String article_url;

    @Enumerated(EnumType.STRING)
    private CaseType type;

    private String word_ref;


    @Builder
    public Case(String case_detail, String description, String article_url, CaseType type, String word_ref ){
        this.case_detail = case_detail;
        this.description = description;
        this.article_url = article_url;
        this.type = type;
        this.word_ref = word_ref;

    }
}

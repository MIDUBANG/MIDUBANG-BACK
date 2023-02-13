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

    private String desc;
    private String article_url;

    @Enumerated(EnumType.STRING)
    private CaseType type;

    private String word_ref;


    @Builder
    public Case(String desc, String article_url, CaseType type, String word_ref){
        this.desc = desc;
        this.article_url = article_url;
        this.type = type;
        this.word_ref = word_ref;

    }
}

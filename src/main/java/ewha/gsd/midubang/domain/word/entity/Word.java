package ewha.gsd.midubang.domain.word.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="word")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="word_id")
    private Long wordId;

    @Column(length = 50)
    private String word;

    @Column(columnDefinition = "TEXT")
    private String meaning;



    @OneToMany(mappedBy = "word")
    private List<MemberWord> memberWordList = new ArrayList<MemberWord>();

    @Builder
    public  Word(String word, String meaning, List<MemberWord> memberWordList){
        this.word = word;
        this.meaning = meaning;
        this.memberWordList = memberWordList;
    }

}

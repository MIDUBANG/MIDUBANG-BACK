package ewha.gsd.midubang.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name="member_word")
public class MemberWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id",referencedColumnName = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="word_id",referencedColumnName = "word_id")
    private Word word;

    @Column(name="word_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime word_date;
    @Builder
    public MemberWord(Member member, Word word,LocalDateTime word_date){
        this.member = member;
        this.word = word;
        this.word_date = word_date;
    }
}

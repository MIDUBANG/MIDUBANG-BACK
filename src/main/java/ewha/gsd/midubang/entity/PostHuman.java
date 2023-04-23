package ewha.gsd.midubang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post_human")
public class PostHuman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column
    private String question;

    @Column
    private String detail;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Member authorId;
}

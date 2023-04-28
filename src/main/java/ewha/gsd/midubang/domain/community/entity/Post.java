package ewha.gsd.midubang.domain.community.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ewha.gsd.midubang.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private Member writer;

    @Column
    private String question;

    @Column
    private String detail;

    @Column(name = "created_date")
    private String createdDate;

    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    private List<Comment> comments;

}

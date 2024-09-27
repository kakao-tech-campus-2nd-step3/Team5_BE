package ojosama.talkak.member.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ojosama.talkak.comment.model.Comment;
import ojosama.talkak.common.exception.TalKakException;
import ojosama.talkak.common.exception.code.MemberError;

@Entity
@Table(name = "member")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String imageUrl;
    private String email;
    private Boolean gender;
    private Integer age;
    private Integer membership;
    private Integer point;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    public void updateMemberInfo(String gender, Integer age) {
        if (!gender.matches("남자|여자") || age == null || age < 10 || age > 100) {
            throw TalKakException.of(MemberError.ERROR_UPDATE_MEMBER_INFO);
        }

        this.gender = !gender.equals("남자");
        this.age = age;
    }
}

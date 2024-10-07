package ojosama.talkak.member.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import ojosama.talkak.comment.domain.Comment;
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
    @Column(unique = true)
    private String email;
    private Boolean gender;
    @Enumerated(EnumType.STRING)
    private Age age;
    @Enumerated(EnumType.STRING)
    private MembershipTier membership;
    private Integer point;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;
    
    public Member(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    private Member(String username, String imageUrl, String email) {
        this.username = username;
        this.imageUrl = imageUrl;
        this.email = email;
    }

    public static Member of(String username, String imageUrl, String email) {
        return new Member(username, imageUrl, email);
    }
  
    public void updateMemberInfo(String gender, String age) {
        Age newAge = Age.fromName(age);
        if (!gender.matches("남자|여자") || newAge == null) {
            throw TalKakException.of(MemberError.ERROR_UPDATE_MEMBER_INFO);
        }
      
        this.gender = !gender.equals("남자");
        this.age = newAge;
    }
}

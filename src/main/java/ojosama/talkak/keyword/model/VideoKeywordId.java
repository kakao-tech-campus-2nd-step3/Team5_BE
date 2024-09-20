package ojosama.talkak.keyword.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter @Setter
public class VideoKeywordId implements Serializable {

    @Column(name = "video_id")
    private Long videoId;
    @Column(name = "keyword_id")
    private Long keywordId;
}

package ojosama.talkak.comment.dto;

import ojosama.talkak.member.dto.MemberResponse;

public record CommentResponse(long commentId, MemberResponse member, String content) {

}

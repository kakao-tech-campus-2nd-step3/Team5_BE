package ojosama.talkak.comment;

import ojosama.talkak.user.MemberResponse;

public record CommentResponse(long commentId, MemberResponse member, String content) {

}

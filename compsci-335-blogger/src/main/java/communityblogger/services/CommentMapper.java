package communityblogger.services;

import communityblogger.domain.Comment;

public class CommentMapper {

	static Comment toDomainModel(communityblogger.dto.Comment dtoComment) {
		Comment fullComment = new Comment(dtoComment.getContent(),
				dtoComment.getTimePosted());
		return fullComment;
	}

	static communityblogger.dto.Comment toDto(Comment _comment) {
		communityblogger.dto.Comment dtoComment = new communityblogger.dto.Comment(
				_comment.getContent(), _comment.getTimePosted(),
				_comment.getAuthor());
		return dtoComment;
	}

}

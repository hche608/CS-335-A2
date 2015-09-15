package communityblogger.services;

import java.util.HashSet;
import java.util.Set;

import communityblogger.domain.Comment;

public class CommentMapper {

	static Comment toDomainModel(communityblogger.dto.Comment dtoComment) {
		Comment fullComment = new Comment(dtoComment.getContent(),
				dtoComment.getTimePosted());
		return fullComment;
	}

	static communityblogger.dto.Comment toDto(Comment _comment) {
		communityblogger.dto.Comment dtoComment = new communityblogger.dto.Comment(
				_comment.getContent(), _comment.getTimePosted());
		dtoComment.setAuthorUsernames(_comment.getAuthor());
		return dtoComment;
	}

	static Set<communityblogger.dto.Comment> toDto(Set<Comment> _comments) {
		Set<communityblogger.dto.Comment> dtoComments = new HashSet<communityblogger.dto.Comment>();
		for (Comment c : _comments) {
			dtoComments.add(CommentMapper.toDto(c));
		}
		return dtoComments;
	}
}

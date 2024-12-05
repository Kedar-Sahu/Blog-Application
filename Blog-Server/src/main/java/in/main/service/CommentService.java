package in.main.service;

import java.util.List;

import in.main.entity.Comment;

public interface CommentService {

	
	Comment createComment(Long id,String postedBy,String content);
	List<Comment> getCommentsByPostId(Long id);
	Boolean deleteComment(Long id);
}
